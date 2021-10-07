package com.acomodeo.backend.contentcaching.service

import com.acomodeo.backend.contentcaching.model.Content
import com.acomodeo.backend.contentcaching.model.Header
import com.acomodeo.backend.contentcaching.model.HostConfig
import com.acomodeo.backend.contentcaching.repository.ContentCachingRepository
import com.acomodeo.backend.contentcaching.repository.ContentMongoRepo
import com.acomodeo.foundation.general.log
import com.acomodeo.semanticid.SemanticId
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.net.URL
import java.nio.charset.Charset
import java.util.Base64

@EnableAutoConfiguration
@Service
class ContentCachingService(
    val hostConfig: HostConfig,
    val header: Header,
    val contentMongoRepo: ContentMongoRepo,
    val contentCachingRepository: ContentCachingRepository,
    var savedId: String = ""
) {
    // Encoding to Base64
    fun encoder(hostConfig: HostConfig): String {
        var base64: String
        try {
            val bytes = URL(hostConfig.url).readBytes()
            base64 = Base64.getEncoder().encodeToString(bytes)
        } catch (e: java.io.FileNotFoundException) {
            log.info("Caught a FileNotFound exception")
            base64 = contentCachingRepository.findAll().last().content
        }
        return base64
    }

    // Decoding to ByteArray
    fun decoder(base64Str: String): String {
        val byteArray = Base64.getDecoder().decode(base64Str)
        return byteArray.toString(Charset.forName(header.charset))
    }

    @Async
    @Cacheable("contentCache")
    fun saveContent(header: Header): String {
        when (savedId.isEmpty() && contentMongoRepo.findByUrl(hostConfig.url).isEmpty()) {
            true -> {
                val newLoadedContent =
                    Content(SemanticId(collection = "content"),
                        encoder(hostConfig), header.charset, header.type, hostConfig.url)
                savedId = newLoadedContent.id.toString()
                contentMongoRepo.deleteById(contentCachingRepository.findAll().last().id.toString())
                contentMongoRepo.insert(newLoadedContent)
                contentMongoRepo.save(newLoadedContent)
                return encoder(hostConfig)
            }
            false -> {
                return contentCachingRepository.findAll().last().content
            }
        }
    }

    // Cache evict policy applicable here
    @CacheEvict("contentCache")
    fun clearCache(hostConfig: HostConfig) {
        log.info("Cache Cleared")
    }
}

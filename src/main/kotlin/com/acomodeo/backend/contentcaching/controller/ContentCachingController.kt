package com.acomodeo.backend.contentcaching.controller

import com.acomodeo.backend.contentcaching.model.Header
import com.acomodeo.backend.contentcaching.model.HostConfig
import com.acomodeo.backend.contentcaching.service.ContentCachingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ContentCachingController(
    val hostConfig: HostConfig,
    val contentcachingService: ContentCachingService
) {
    @GetMapping("/")
    fun cachedResult(header: Header): String {
        return contentcachingService.decoder(contentcachingService.saveContent(header))
    }

    @GetMapping("/clear-cache")
    fun clearCache(header: Header): String {
        contentcachingService.clearCache(hostConfig)
        return contentcachingService.decoder(contentcachingService.saveContent(header))
    }
}

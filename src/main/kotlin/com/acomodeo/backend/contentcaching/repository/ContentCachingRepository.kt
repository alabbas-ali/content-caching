package com.acomodeo.backend.contentcaching.repository

import com.acomodeo.backend.contentcaching.model.Content
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ContentCachingRepository : MongoRepository<Content, String>

interface ContentMongoRepo : MongoRepository<Content, String> {

    fun findByUrl(url: String): List<Content>
}

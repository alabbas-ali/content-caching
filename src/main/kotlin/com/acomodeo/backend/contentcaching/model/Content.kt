package com.acomodeo.backend.contentcaching.model

import com.acomodeo.semanticid.SemanticId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Content(
    @Id
    var id: SemanticId = SemanticId(collection = "content"),
    var content: String,
    var charset: String = "",
    var type: String = "",
    var url: String = ""
)

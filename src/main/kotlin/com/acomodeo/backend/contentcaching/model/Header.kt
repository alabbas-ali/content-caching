package com.acomodeo.backend.contentcaching.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "header")
class Header {
    var charset: String = ""
    var type: String = ""
}

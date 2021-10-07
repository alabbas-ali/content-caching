package com.acomodeo.backend.contentcaching.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "host")
class HostConfig {
    lateinit var url: String
}

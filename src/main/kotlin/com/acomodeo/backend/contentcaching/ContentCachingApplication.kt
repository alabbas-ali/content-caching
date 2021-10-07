package com.acomodeo.backend.contentcaching

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ContentCachingApplication

fun main(args: Array<String>) {

    SpringApplication.run(ContentCachingApplication::class.java, *args)
}

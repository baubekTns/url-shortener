package com.baubekTns.url_shortener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(

        String baseUrl,

        Integer rateLimit,

        Long rateLimitWindowSeconds

) {
}

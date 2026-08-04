package com.baubekTns.url_shortener.dto;

import java.time.LocalDateTime;

public record UrlAnalyticsResponse(

        String shortCode,

        String originalUrl,

        Long clickCount,

        LocalDateTime createdAt,

        LocalDateTime lastAccessedAt,

        LocalDateTime expiresAt

) {
}

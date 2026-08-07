package com.baubekTns.url_shortener.dto;

import java.time.LocalDateTime;

public record UserUrlResponse(
        String shortCode,
        String shortUrl,
        String originalUrl,
        Long clickCount,
        LocalDateTime createdAt,
        LocalDateTime expiresAt
) {
}

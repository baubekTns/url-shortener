package com.baubekTns.url_shortener.dto.cache;

import java.time.LocalDateTime;

public record CachedUrl(
        String originalUrl,
        LocalDateTime expiresAt
) {
}

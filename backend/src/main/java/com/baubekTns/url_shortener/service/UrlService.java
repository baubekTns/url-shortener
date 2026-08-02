package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.dto.ShortUrlResponse;

public interface UrlService {

    ShortUrlResponse createShortUrl(
            String originalUrl,
            Integer expiresInDays
    );

    String getOriginalUrl(String shortCode);

}

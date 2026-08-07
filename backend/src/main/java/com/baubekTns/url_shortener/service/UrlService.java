package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.dto.UserUrlResponse;

import java.util.List;

public interface UrlService {

    ShortUrlResponse createShortUrl(
            String originalUrl,
            Integer expiresInDays,
            String userEmail
    );

    String getOriginalUrl(String shortCode);

    List<UserUrlResponse> getUrlsForUser(String userEmail);
}

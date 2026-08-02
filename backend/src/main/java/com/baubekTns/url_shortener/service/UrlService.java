package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.entity.Url;

public interface UrlService {

    ShortUrlResponse createShortUrl(String originalUrl);

    Url getByShortCode(String shortCode);
}
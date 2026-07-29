package com.baubekTns.url_shortener.dto;


public record ShortUrlResponse(

        String shortCode,
        String shortUrl,
        String originalUrl

) {}

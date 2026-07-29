package com.baubekTns.url_shortener.controller;

import com.baubekTns.url_shortener.dto.CreateShortUrlRequest;
import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.entity.Url;
import com.baubekTns.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ShortUrlResponse createShortUrl(
            @Valid @RequestBody CreateShortUrlRequest request
    ) {

        Url url = urlService.createShortUrl(request.url());

        return new ShortUrlResponse(
            url.getShortCode(),
            "http://localhost:8080/" + url.getShortCode(),
            url.getOriginalUrl()
        );
    }
}

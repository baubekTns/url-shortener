package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.entity.Url;
import com.baubekTns.url_shortener.repository.UrlRepository;
import com.baubekTns.url_shortener.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    public Url createShortUrl(String originalUrl) {

        String shortCode;

        do {
            shortCode = shortCodeGenerator.generate();
        } while (urlRepository.findByShortCode(shortCode).isPresent());

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCode)
                .createdAt(LocalDateTime.now())
                .build();

        return urlRepository.save(url);
    }
}

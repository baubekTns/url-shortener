package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.entity.Url;
import com.baubekTns.url_shortener.exception.UrlNotFoundException;
import com.baubekTns.url_shortener.mapper.UrlMapper;
import com.baubekTns.url_shortener.repository.UrlRepository;
import com.baubekTns.url_shortener.service.ShortCodeService;
import com.baubekTns.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final ShortCodeService shortCodeService;
    private final UrlMapper urlMapper;

    @Override
    public ShortUrlResponse createShortUrl(String originalUrl) {

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCodeService.generateUniqueShortCode())
                .createdAt(LocalDateTime.now())
                .build();

        Url saved = urlRepository.save(url);

        return urlMapper.toResponse(saved);
    }

    @Override
    public Url getByShortCode(String shortCode) {

        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));

    }
}

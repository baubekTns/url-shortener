package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.dto.UrlAnalyticsResponse;
import com.baubekTns.url_shortener.entity.Url;
import com.baubekTns.url_shortener.exception.UrlNotFoundException;
import com.baubekTns.url_shortener.repository.UrlRepository;
import com.baubekTns.url_shortener.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final UrlRepository urlRepository;

    @Override
    public void recordRedirect(String shortCode) {

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new UrlNotFoundException(shortCode)
                );

        url.setClickCount(url.getClickCount() + 1);
        url.setLastAccessedAt(LocalDateTime.now());

        urlRepository.save(url);
    }

    @Override
    public UrlAnalyticsResponse getAnalytics(
            String shortCode,
            String userEmail
    ) {

        Url url = urlRepository
                .findByShortCodeAndUserEmail(
                        shortCode,
                        userEmail
                )
                .orElseThrow(() ->
                        new UrlNotFoundException(shortCode)
                );

        return new UrlAnalyticsResponse(
                url.getShortCode(),
                url.getOriginalUrl(),
                url.getClickCount(),
                url.getCreatedAt(),
                url.getLastAccessedAt(),
                url.getExpiresAt()
        );
    }
}

package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.dto.cache.CachedUrl;
import com.baubekTns.url_shortener.entity.Url;
import com.baubekTns.url_shortener.exception.UrlExpiredException;
import com.baubekTns.url_shortener.exception.UrlNotFoundException;
import com.baubekTns.url_shortener.mapper.UrlMapper;
import com.baubekTns.url_shortener.repository.UrlRepository;
import com.baubekTns.url_shortener.service.AnalyticsService;
import com.baubekTns.url_shortener.service.CacheService;
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
    private final CacheService cacheService;
    private final AnalyticsService analyticsService;
    private final UrlMapper urlMapper;

    @Override
    public ShortUrlResponse createShortUrl(
            String originalUrl,
            Integer expiresInDays
    ) {

        LocalDateTime expiresAt = null;

        if (expiresInDays != null) {
            expiresAt = LocalDateTime.now().plusDays(expiresInDays);
        }

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(shortCodeService.generateUniqueShortCode())
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .build();

        Url saved = urlRepository.save(url);

        return urlMapper.toResponse(saved);
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        CachedUrl cachedUrl = cacheService.get(shortCode);

        if (cachedUrl != null) {

            if (cachedUrl.expiresAt() != null &&
                    cachedUrl.expiresAt().isBefore(LocalDateTime.now())) {

                throw new UrlExpiredException(shortCode);
            }

            analyticsService.recordRedirect(shortCode);

            return cachedUrl.originalUrl();
        }

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(shortCode));

        if (url.getExpiresAt() != null &&
                url.getExpiresAt().isBefore(LocalDateTime.now())) {

            throw new UrlExpiredException(shortCode);
        }

        cacheService.put(
                shortCode,
                new CachedUrl(
                        url.getOriginalUrl(),
                        url.getExpiresAt()
                )
        );

        analyticsService.recordRedirect(shortCode);

        return url.getOriginalUrl();
    }

}

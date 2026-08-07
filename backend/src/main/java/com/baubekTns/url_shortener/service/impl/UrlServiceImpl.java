package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.config.AppProperties;
import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.dto.UserUrlResponse;
import com.baubekTns.url_shortener.dto.cache.CachedUrl;
import com.baubekTns.url_shortener.entity.Url;
import com.baubekTns.url_shortener.entity.User;
import com.baubekTns.url_shortener.exception.UrlExpiredException;
import com.baubekTns.url_shortener.exception.UrlNotFoundException;
import com.baubekTns.url_shortener.mapper.UrlMapper;
import com.baubekTns.url_shortener.repository.UrlRepository;
import com.baubekTns.url_shortener.repository.UserRepository;
import com.baubekTns.url_shortener.service.AnalyticsService;
import com.baubekTns.url_shortener.service.CacheService;
import com.baubekTns.url_shortener.service.ShortCodeService;
import com.baubekTns.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private final ShortCodeService shortCodeService;
    private final CacheService cacheService;
    private final AnalyticsService analyticsService;
    private final UrlMapper urlMapper;
    private final AppProperties appProperties;

    @Override
    public ShortUrlResponse createShortUrl(
            String originalUrl,
            Integer expiresInDays,
            String userEmail
    ) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user could not be found."
                        )
                );

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expiresAt =
                expiresInDays == null
                        ? null
                        : now.plusDays(expiresInDays);

        Url url = Url.builder()
                .originalUrl(originalUrl)
                .shortCode(
                        shortCodeService.generateUniqueShortCode()
                )
                .createdAt(now)
                .expiresAt(expiresAt)
                .user(user)
                .build();

        Url saved = urlRepository.save(url);

        return urlMapper.toResponse(saved);
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        CachedUrl cachedUrl = cacheService.get(shortCode);

        if (cachedUrl != null) {

            if (isExpired(cachedUrl.expiresAt())) {
                cacheService.evict(shortCode);
                throw new UrlExpiredException(shortCode);
            }

            analyticsService.recordRedirect(shortCode);

            return cachedUrl.originalUrl();
        }

        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new UrlNotFoundException(shortCode)
                );

        if (isExpired(url.getExpiresAt())) {
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

    @Override
    public List<UserUrlResponse> getUrlsForUser(
            String userEmail
    ) {

        return urlRepository
                .findAllByUserEmailOrderByCreatedAtDesc(userEmail)
                .stream()
                .map(this::toUserUrlResponse)
                .toList();
    }

    private UserUrlResponse toUserUrlResponse(Url url) {

        return new UserUrlResponse(
                url.getShortCode(),
                appProperties.baseUrl()
                        + "/api/v1/urls/"
                        + url.getShortCode(),
                url.getOriginalUrl(),
                url.getClickCount(),
                url.getCreatedAt(),
                url.getExpiresAt()
        );
    }

    private boolean isExpired(LocalDateTime expiresAt) {

        return expiresAt != null
                && expiresAt.isBefore(LocalDateTime.now());
    }
}

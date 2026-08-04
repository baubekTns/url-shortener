package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.config.AppProperties;
import com.baubekTns.url_shortener.service.RateLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitServiceImpl implements RateLimitService {

    private static final String KEY_PREFIX = "rate:";

    private final StringRedisTemplate redisTemplate;
    private final AppProperties appProperties;

    @Override
    public boolean allowRequest(String clientIp) {

        String key = KEY_PREFIX + clientIp;

        Long currentRequests = redisTemplate.opsForValue().increment(key);

        if (currentRequests == null) {
            return false;
        }

        if (currentRequests == 1L) {
            redisTemplate.expire(
                    key,
                    Duration.ofSeconds(appProperties.rateLimitWindowSeconds())
            );
        }

        return currentRequests <= appProperties.rateLimit();
    }

}

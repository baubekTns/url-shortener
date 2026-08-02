package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.service.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private static final Duration TTL = Duration.ofHours(24);

    private final StringRedisTemplate redisTemplate;

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value, TTL);
    }

}

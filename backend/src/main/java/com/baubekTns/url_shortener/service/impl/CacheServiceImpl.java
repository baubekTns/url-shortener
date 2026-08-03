package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.dto.cache.CachedUrl;
import com.baubekTns.url_shortener.service.CacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private static final Duration TTL = Duration.ofHours(24);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public CachedUrl get(String shortCode) {

        String json = redisTemplate.opsForValue().get(shortCode);

        if (json == null) {
            return null;
        }

        try {
            return objectMapper.readValue(json, CachedUrl.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize cached URL", e);
        }
    }

    @Override
    public void put(String shortCode, CachedUrl cachedUrl) {

        try {

            String json = objectMapper.writeValueAsString(cachedUrl);

            redisTemplate.opsForValue().set(
                    shortCode,
                    json,
                    TTL
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize cached URL", e);
        }
    }

    @Override
    public void evict(String shortCode) {
        redisTemplate.delete(shortCode);
    }

}

package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.dto.cache.CachedUrl;

public interface CacheService {

    CachedUrl get(String shortCode);

    void put(String shortCode, CachedUrl cachedUrl);

    void evict(String shortCode);

}

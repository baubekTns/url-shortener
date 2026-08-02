package com.baubekTns.url_shortener.service;

public interface CacheService {

    String get(String key);

    void put(String key, String value);

}

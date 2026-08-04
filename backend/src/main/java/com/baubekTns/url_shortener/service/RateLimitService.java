package com.baubekTns.url_shortener.service;

public interface RateLimitService {

    boolean allowRequest(String clientIp);

}

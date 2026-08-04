package com.baubekTns.url_shortener.service;

import com.baubekTns.url_shortener.dto.UrlAnalyticsResponse;

public interface AnalyticsService {

    void recordRedirect(String shortCode);

    UrlAnalyticsResponse getAnalytics(String shortCode);

}

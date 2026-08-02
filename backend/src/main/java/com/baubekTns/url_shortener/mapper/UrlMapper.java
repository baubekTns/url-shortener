package com.baubekTns.url_shortener.mapper;

import com.baubekTns.url_shortener.config.AppProperties;
import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.entity.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlMapper {

    private final AppProperties appProperties;

    public ShortUrlResponse toResponse(Url url) {

        return new ShortUrlResponse(
                url.getShortCode(),
                appProperties.baseUrl() + "/" + url.getShortCode(),
                url.getOriginalUrl()
        );
    }
}

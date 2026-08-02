package com.baubekTns.url_shortener.mapper;

import com.baubekTns.url_shortener.dto.ShortUrlResponse;
import com.baubekTns.url_shortener.entity.Url;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {

    @Value("${app.base-url}")
    private String baseUrl;

    public ShortUrlResponse toResponse(Url url) {

        return new ShortUrlResponse(
                url.getShortCode(),
                baseUrl + "/" + url.getShortCode(),
                url.getOriginalUrl()
        );
    }

}

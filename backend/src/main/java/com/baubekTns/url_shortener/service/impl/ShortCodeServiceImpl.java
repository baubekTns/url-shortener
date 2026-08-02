package com.baubekTns.url_shortener.service.impl;

import com.baubekTns.url_shortener.repository.UrlRepository;
import com.baubekTns.url_shortener.service.ShortCodeService;
import com.baubekTns.url_shortener.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortCodeServiceImpl implements ShortCodeService {

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;

    @Override
    public String generateUniqueShortCode() {

        String shortCode;

        do {
            shortCode = shortCodeGenerator.generate();
        } while (urlRepository.findByShortCode(shortCode).isPresent());

        return shortCode;
    }
}

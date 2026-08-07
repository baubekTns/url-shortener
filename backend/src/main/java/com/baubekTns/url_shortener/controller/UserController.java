package com.baubekTns.url_shortener.controller;

import com.baubekTns.url_shortener.dto.UserUrlResponse;
import com.baubekTns.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UrlService urlService;

    @GetMapping("/me/urls")
    public ResponseEntity<List<UserUrlResponse>> getMyUrls(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                urlService.getUrlsForUser(
                        authentication.getName()
                )
        );
    }
}

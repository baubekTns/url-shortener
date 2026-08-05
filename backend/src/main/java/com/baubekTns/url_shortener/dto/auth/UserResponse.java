package com.baubekTns.url_shortener.dto.auth;

import java.time.LocalDateTime;

public record UserResponse(

        Long id,

        String fullName,

        String email,

        LocalDateTime createdAt

) {
}

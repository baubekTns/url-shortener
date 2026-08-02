package com.baubekTns.url_shortener.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateShortUrlRequest(

        @NotBlank(message = "URL cannot be blank")
        @Pattern(
                regexp = "^(https?://).+",
                message = "URL must start with http:// or https://"
        )
        String url,

        @Min(value = 1, message = "Expiration must be at least 1 day")
        Integer expiresInDays

) {
}

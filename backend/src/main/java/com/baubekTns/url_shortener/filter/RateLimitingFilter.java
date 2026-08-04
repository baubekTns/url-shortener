package com.baubekTns.url_shortener.filter;

import com.baubekTns.url_shortener.exception.RateLimitExceededException;
import com.baubekTns.url_shortener.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        // Skip health check and Swagger endpoints
        if (requestUri.startsWith("/actuator")
                || requestUri.startsWith("/swagger-ui")
                || requestUri.startsWith("/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getClientIp(request);

        if (!rateLimitService.allowRequest(clientIp)) {
            throw new RateLimitExceededException();
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {

        String forwarded = request.getHeader("X-Forwarded-For");

        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }

}

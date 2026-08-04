package com.baubekTns.url_shortener.exception;

public class RateLimitExceededException extends RuntimeException {

    public RateLimitExceededException() {
        super("Rate limit exceeded. Please try again later.");
    }

}

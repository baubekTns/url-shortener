package com.baubekTns.url_shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleUrlNotFound(
            UrlNotFoundException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setTitle("URL Not Found");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(UrlExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ProblemDetail handleExpired(
            UrlExpiredException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.GONE);

        problem.setTitle("URL Expired");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ProblemDetail handleRateLimitExceeded(
            RateLimitExceededException ex
    ) {

        ProblemDetail problem =
                ProblemDetail.forStatus(HttpStatus.TOO_MANY_REQUESTS);

        problem.setTitle("Rate Limit Exceeded");
        problem.setDetail(ex.getMessage());

        return problem;
    }

}

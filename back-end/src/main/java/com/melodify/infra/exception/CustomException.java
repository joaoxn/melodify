package com.melodify.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CustomException extends ResponseStatusException {
    private HttpStatusCode statusCode;
    private String message;
    private Throwable cause;

    public CustomException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
        this.message = message;
    }

    public CustomException(HttpStatusCode statusCode) {
        super(statusCode);
        this.statusCode = statusCode;
    }

    public CustomException(HttpStatusCode statusCode, String message) {
        super(statusCode, message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public CustomException(HttpStatusCode statusCode, String message, Throwable cause) {
        super(statusCode, message, cause);
        this.statusCode = statusCode;
        this.message = message;
        this.cause = cause;
    }
}
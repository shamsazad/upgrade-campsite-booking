package com.upgrade.campsitereservation.exception;

import org.springframework.http.HttpStatus;

public class AlreadyBookedException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ErrorResponse errorResponse;

    public AlreadyBookedException(String message) {
        super(message);
        this.httpStatus = null;
        errorResponse = null;
    }

    public AlreadyBookedException(String message, HttpStatus httpStatus, ErrorResponse errorResponse) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorResponse = errorResponse;
    }

    public AlreadyBookedException(String message, Throwable cause, HttpStatus httpStatus, ErrorResponse errorResponse) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorResponse = errorResponse;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}

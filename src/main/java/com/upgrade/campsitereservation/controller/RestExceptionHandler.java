package com.upgrade.campsitereservation.controller;

import com.upgrade.campsitereservation.exception.AlreadyBookedException;
import com.upgrade.campsitereservation.exception.ErrorResponse;
import com.upgrade.campsitereservation.exception.ErrorResponseFactory;
import com.upgrade.campsitereservation.exception.MaximumBookingDayLimitExceedException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaximumBookingDayLimitExceedException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(MaximumBookingDayLimitExceedException maximumBookingDayLimitExceedException) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildBadRequestExceededMaximumBookingDaysAllowed(maximumBookingDayLimitExceedException.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(AlreadyBookedException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(AlreadyBookedException alreadyBookedException) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildBadRequestAlreadyBookedException(alreadyBookedException.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(NoSuchElementException e) {

        ErrorResponse errorResponse = ErrorResponseFactory
                .buildEntityNotFoundException(e.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ConstraintViolationException constraintViolationException) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildForgienKeyConstraintException(constraintViolationException.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequestException badRequestException) {

        ErrorResponse errorResponse = ErrorResponseFactory
                .buildBadRequestException(badRequestException.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(HttpServerErrorException.InternalServerError internalServerError) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildInternalServerError(internalServerError.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler( Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {

        ErrorResponse errorResponse = ErrorResponseFactory
                .buildInternalServerError(e.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    private static ResponseEntity<ErrorResponse> buildBadRequestErrorResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity.badRequest().body(errorResponse);
    }
}


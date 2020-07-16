package com.upgrade.campsitereservation.controller;

import com.upgrade.campsitereservation.exception.AlreadyBookedException;
import com.upgrade.campsitereservation.exception.ErrorResponse;
import com.upgrade.campsitereservation.exception.ErrorResponseFactory;
import com.upgrade.campsitereservation.exception.MaximumBookingDayLimitExceedException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.BadRequestException;
import java.util.NoSuchElementException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static String MAXIMUM_NUMBER_BOOKING_DAYS_ALLOWED = "The maximum number of booking days allowed per request is 3.";
    private static String MISSING_ENTITY = "Unable to find the Booking, please review your bookingId";
    private static String ALREADY_BOOKED = "The campsite is already booked for the given date.";

    @ExceptionHandler(MaximumBookingDayLimitExceedException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(MaximumBookingDayLimitExceedException maximumBookingDayLimitExceedException) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildBadRequestExceededMaximumBookingDaysAllowed(MAXIMUM_NUMBER_BOOKING_DAYS_ALLOWED);

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(AlreadyBookedException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(AlreadyBookedException alreadyBookedException) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildBadRequestAlreadyBookedException(ALREADY_BOOKED);

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(NoSuchElementException e) {

        ErrorResponse errorResponse = ErrorResponseFactory
                .buildEntityNotFoundException(MISSING_ENTITY);

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ConstraintViolationException constraintViolationException) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildForgienKeyConstraintException(constraintViolationException.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(HttpServerErrorException.InternalServerError internalServerError) {
        ErrorResponse errorResponse = ErrorResponseFactory
                .buildInternalServerError(internalServerError.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequestException badRequestException) {

        ErrorResponse errorResponse = ErrorResponseFactory
                .buildBadRequestException(badRequestException.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {

        ErrorResponse errorResponse = ErrorResponseFactory
                .buildInternalServerError(e.getMessage());

        return buildBadRequestErrorResponseEntity(errorResponse);
    }

    private static ResponseEntity<ErrorResponse> buildBadRequestErrorResponseEntity(ErrorResponse errorResponse) {
        return ResponseEntity.badRequest().body(errorResponse);
    }
}


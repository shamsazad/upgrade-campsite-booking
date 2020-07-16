package com.upgrade.campsitereservation.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponseFactory {

    private static final String ALREADY_BOOKED = "booked.already.campsite";
    private static final String BOOKING_DAYS_LIMIT = "booking.days.limit.exceed";
    private static final String ENTITY_MISSING = "entity.missing";
    private static final String INTERNAL_SERVER_ERROR = "internal.server.error";
    private static final String BAD_REQUEST = "bad.request.error";

    public static ErrorResponse buildBadRequestAlreadyBookedException(String message) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(ALREADY_BOOKED)
                .type(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }

    public static ErrorResponse buildBadRequestExceededMaximumBookingDaysAllowed(String message) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(BOOKING_DAYS_LIMIT)
                .type(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }

    public static ErrorResponse buildEntityNotFoundException(String message) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(ENTITY_MISSING)
                .type(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }

    public static ErrorResponse buildForgienKeyConstraintException(String message) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(ENTITY_MISSING)
                .type(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }

    public static ErrorResponse buildInternalServerError(String message) {

        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(INTERNAL_SERVER_ERROR)
                .type(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message(message)
                .build();
    }

    public static ErrorResponse buildBadRequestException(String message) {

        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(BAD_REQUEST)
                .type(HttpStatus.BAD_REQUEST.name())
                .message(message)
                .build();
    }
}


package com.upgrade.campsitereservation.util;

import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.dto.response.BookingResponse;
import com.upgrade.campsitereservation.entity.model.Booking;

import java.time.LocalDateTime;

public class BookingMapper {

    public static BookingResponse bookingToBookingResponse(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setArrivalDate(booking.getArrivalDate());
        bookingResponse.setDepartureDate(booking.getDepartureDate());
        bookingResponse.setBookingId(booking.getId());
        bookingResponse.setName(booking.getFirstName().concat(" ").concat(booking.getLastName()));
        return bookingResponse;
    }

    public static Booking createBookingRequestToBooking(BookingRequest.CreateBookingRequest createBookingRequest) {
        Booking booking = new Booking();
        booking.setArrivalDate(createBookingRequest.getArrivalDate());
        booking.setDepartureDate(createBookingRequest.getDepartureDate());
        booking.setEmail(createBookingRequest.getEmail());
        booking.setFirstName(createBookingRequest.getFirstName());
        booking.setLastName(createBookingRequest.getLastName());
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());
        return booking;
    }

    public static Booking updateBookingRequestToBooking(Booking booking, BookingRequest.UpdateBookingRequest updateBookingRequest) {

        booking.setArrivalDate(updateBookingRequest.getArrivalDate());
        booking.setDepartureDate(updateBookingRequest.getDepartureDate());
        booking.setUpdatedAt(LocalDateTime.now());
        return booking;
    }
}

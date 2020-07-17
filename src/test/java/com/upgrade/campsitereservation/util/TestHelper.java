package com.upgrade.campsitereservation.util;

import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.model.Booking;
import com.upgrade.campsitereservation.entity.model.CampsiteBookedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestHelper {

    public static List<CampsiteBookedDate> createCampsiteBookingDatesForGivenId() {

        List<CampsiteBookedDate> campsiteBookedDates = new ArrayList<>();
        CampsiteBookedDate campsiteBookedDate1 = new CampsiteBookedDate();
        campsiteBookedDate1.setBookedDate(LocalDate.now().plusDays(1));
        campsiteBookedDate1.setBooking(createBooking().get());

        CampsiteBookedDate campsiteBookedDate2 = new CampsiteBookedDate();
        campsiteBookedDate2.setBookedDate(LocalDate.now().plusDays(2));
        campsiteBookedDate2.setBooking(createBooking().get());

        campsiteBookedDates.add(campsiteBookedDate1);
        campsiteBookedDates.add(campsiteBookedDate2);

        return campsiteBookedDates;
    }

    public static BookingRequest.UpdateBookingRequest createUpdateRequest() {

        BookingRequest.UpdateBookingRequest updateBookingRequest = new BookingRequest.UpdateBookingRequest();
        updateBookingRequest.setArrivalDate(LocalDate.now().plusDays(9));
        updateBookingRequest.setDepartureDate(LocalDate.now().plusDays(10));
        updateBookingRequest.setBookingId(1);
        return updateBookingRequest;
    }

    public static List<CampsiteBookedDate> createCampsiteBookingDates() {

        List<CampsiteBookedDate> campsiteBookedDates = new ArrayList<>();
        CampsiteBookedDate campsiteBookedDate1 = new CampsiteBookedDate();
        campsiteBookedDate1.setBookedDate(LocalDate.now().plusDays(1));
        campsiteBookedDate1.setBooking(createBooking().get());

        CampsiteBookedDate campsiteBookedDate2 = new CampsiteBookedDate();
        campsiteBookedDate2.setBookedDate(LocalDate.now().plusDays(2));
        campsiteBookedDate2.setBooking(createBooking().get());

        Booking booking = createBooking().get();
        booking.setId(2);
        CampsiteBookedDate campsiteBookedDate3 = new CampsiteBookedDate();
        campsiteBookedDate3.setBookedDate(LocalDate.now().plusDays(3));
        campsiteBookedDate3.setBooking(booking);

        CampsiteBookedDate campsiteBookedDate4 = new CampsiteBookedDate();
        campsiteBookedDate4.setBookedDate(LocalDate.now().plusDays(4));
        campsiteBookedDate4.setBooking(booking);

        campsiteBookedDates.add(campsiteBookedDate1);
        campsiteBookedDates.add(campsiteBookedDate2);
        campsiteBookedDates.add(campsiteBookedDate3);
        campsiteBookedDates.add(campsiteBookedDate4);

        return campsiteBookedDates;
    }

    public static Optional<Booking> createBooking() {

        Booking booking = new Booking();
        booking.setFirstName("shams");
        booking.setLastName("Azad");
        booking.setEmail("shams.azad@gmail.com");
        booking.setId(1);
        booking.setArrivalDate(LocalDate.now().plusDays(1));
        booking.setDepartureDate(LocalDate.now().plusDays(3));
        return Optional.of(booking);
    }

    public static BookingRequest.CreateBookingRequest createBookingRequest() {

        BookingRequest.CreateBookingRequest createBookingRequest = new BookingRequest.CreateBookingRequest();
        createBookingRequest.setArrivalDate(LocalDate.now().plusDays(6));
        createBookingRequest.setDepartureDate(LocalDate.now().plusDays(7));
        createBookingRequest.setEmail("shams.azad@gmail.com");
        createBookingRequest.setFirstName("shams");
        createBookingRequest.setLastName("Azad");

        return createBookingRequest;
    }
}

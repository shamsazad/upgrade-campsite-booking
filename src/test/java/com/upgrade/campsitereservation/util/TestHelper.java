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
        campsiteBookedDate1.setBookedDate(LocalDate.of(2020,7,18));
        campsiteBookedDate1.setBooking(createBooking().get());

        CampsiteBookedDate campsiteBookedDate2 = new CampsiteBookedDate();
        campsiteBookedDate2.setBookedDate(LocalDate.of(2020,7,19));
        campsiteBookedDate2.setBooking(createBooking().get());

        campsiteBookedDates.add(campsiteBookedDate1);
        campsiteBookedDates.add(campsiteBookedDate2);

        return campsiteBookedDates;
    }

    public static BookingRequest.UpdateBookingRequest createUpdateRequest() {

        BookingRequest.UpdateBookingRequest updateBookingRequest = new BookingRequest.UpdateBookingRequest();
        updateBookingRequest.setArrivalDate(LocalDate.of(2020,7,5));
        updateBookingRequest.setDepartureDate(LocalDate.of(2020,7,7));
        updateBookingRequest.setBookingId(1);
        return updateBookingRequest;
    }

    public static List<CampsiteBookedDate> createCampsiteBookingDates() {

        List<CampsiteBookedDate> campsiteBookedDates = new ArrayList<>();
        CampsiteBookedDate campsiteBookedDate1 = new CampsiteBookedDate();
        campsiteBookedDate1.setBookedDate(LocalDate.of(2020,7,18));
        campsiteBookedDate1.setBooking(createBooking().get());

        CampsiteBookedDate campsiteBookedDate2 = new CampsiteBookedDate();
        campsiteBookedDate2.setBookedDate(LocalDate.of(2020,7,19));
        campsiteBookedDate2.setBooking(createBooking().get());

        Booking booking = createBooking().get();
        booking.setId(2);
        CampsiteBookedDate campsiteBookedDate3 = new CampsiteBookedDate();
        campsiteBookedDate3.setBookedDate(LocalDate.of(2020,7,20));
        campsiteBookedDate3.setBooking(booking);

        CampsiteBookedDate campsiteBookedDate4 = new CampsiteBookedDate();
        campsiteBookedDate4.setBookedDate(LocalDate.of(2020,7,21));
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
        booking.setArrivalDate(LocalDate.of(2020,7,18));
        booking.setDepartureDate(LocalDate.of(2020,7,20));
        return Optional.of(booking);
    }

    public static BookingRequest.CreateBookingRequest createBookingRequest() {

        BookingRequest.CreateBookingRequest createBookingRequest = new BookingRequest.CreateBookingRequest();
        createBookingRequest.setArrivalDate(LocalDate.of(2020,7,23));
        createBookingRequest.setDepartureDate(LocalDate.of(2020,7,25));
        createBookingRequest.setEmail("shams.azad@gmail.com");
        createBookingRequest.setFirstName("shams");
        createBookingRequest.setLastName("Azad");

        return createBookingRequest;
    }
}

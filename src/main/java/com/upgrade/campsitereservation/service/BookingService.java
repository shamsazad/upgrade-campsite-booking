package com.upgrade.campsitereservation.service;

import com.upgrade.campsitereservation.dao.IBookingDao;
import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.dto.response.BookingResponse;
import com.upgrade.campsitereservation.entity.model.Booking;
import com.upgrade.campsitereservation.exception.AlreadyBookedException;
import com.upgrade.campsitereservation.exception.MaximumBookingDayLimitExceedException;
import com.upgrade.campsitereservation.util.BookingMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BookingService {

    private IBookingDao bookingDao;

    public BookingService(IBookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    public BookingResponse getBookingById(Integer id) {
        Optional<Booking> optionalBooking = bookingDao.findById(id);
        return BookingMapper.bookingToBookingResponse(optionalBooking.get());
    }

    @Transactional
    public BookingResponse bookCampsite(BookingRequest.CreateBookingRequest createBookingRequest) {
        Booking booking = BookingMapper.createBookingRequestToBooking(createBookingRequest);
        if (DAYS.between(createBookingRequest.getArrivalDate(), createBookingRequest.getDepartureDate()) > 3) {
            throw new MaximumBookingDayLimitExceedException("Exceeded number of days");
        }
        if (checkAvailability(createBookingRequest.getArrivalDate(), createBookingRequest.getDepartureDate())) {
            bookingDao.save(booking);
        } else {
            throw new AlreadyBookedException("Already Booked");
        }
        return BookingMapper.bookingToBookingResponse(booking);
    }

    private boolean checkAvailability(LocalDate arrivalDate, LocalDate departureDate) {
        List<Booking> bookings = bookingDao.findAll();

        for (Booking booking : bookings) {
            if (arrivalDate.isEqual(booking.getArrivalDate())
                    || departureDate.isEqual(booking.getDepartureDate())) {
                return false;
            }
            if (arrivalDate.isAfter(booking.getArrivalDate())
                    && arrivalDate.isBefore(booking.getDepartureDate())) {
                return false;
            }
            if (arrivalDate.isBefore(booking.getArrivalDate())
                    && departureDate.isAfter(booking.getArrivalDate())) {
                return false;
            }
        }
        return true;
    }
}

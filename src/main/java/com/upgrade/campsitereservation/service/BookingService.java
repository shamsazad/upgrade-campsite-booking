package com.upgrade.campsitereservation.service;

import com.upgrade.campsitereservation.dao.IBookingDao;
import com.upgrade.campsitereservation.dao.ICampsiteBookedDateDao;
import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.dto.response.BookingResponse;
import com.upgrade.campsitereservation.entity.dto.response.DateAvailabilityResponse;
import com.upgrade.campsitereservation.entity.model.Booking;
import com.upgrade.campsitereservation.entity.model.CampsiteBookedDate;
import com.upgrade.campsitereservation.exception.AlreadyBookedException;
import com.upgrade.campsitereservation.exception.MaximumBookingDayLimitExceedException;
import com.upgrade.campsitereservation.util.BookingMapper;
import com.upgrade.campsitereservation.util.DateAvailabilityMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class BookingService {

    private IBookingDao bookingDao;
    private ICampsiteBookedDateDao campsiteBookedDateDao;

    public BookingService(IBookingDao bookingDao, ICampsiteBookedDateDao campsiteBookedDateDao) {
        this.bookingDao = bookingDao;
        this.campsiteBookedDateDao = campsiteBookedDateDao;
    }

    public BookingResponse getBookingById(Integer id) {
        Optional<Booking> optionalBooking = bookingDao.findById(id);
        return BookingMapper.bookingToBookingResponse(optionalBooking.get());
    }

    @Transactional
    public BookingResponse bookCampsite(BookingRequest.CreateBookingRequest createBookingRequest) {

        Booking booking;

        if (DAYS.between(createBookingRequest.getArrivalDate(), createBookingRequest.getDepartureDate()) > 3) {
            throw new MaximumBookingDayLimitExceedException("Exceeded number of days");
        }

        List<CampsiteBookedDate> campsiteBookedDates = campsiteBookedDateDao.findAll();

        if (checkAvailability(createBookingRequest.getArrivalDate(), createBookingRequest.getDepartureDate(),
                campsiteBookedDates)) {

            booking = BookingMapper.createBookingRequestToBooking(createBookingRequest);
            Booking bookedDetails = bookingDao.save(booking);
            List<CampsiteBookedDate> createdCampsiteBookedDates = createCampsiteBookedDate(createBookingRequest, bookedDetails);
            campsiteBookedDateDao.saveAll(createdCampsiteBookedDates);
        } else {
            throw new AlreadyBookedException("Already Booked");
        }
        return BookingMapper.bookingToBookingResponse(booking);
    }

    @Transactional
    public void deleteBookingById(Integer bookingId) {
        campsiteBookedDateDao.deleteByBookingId(bookingId);
        bookingDao.deleteById(bookingId);
    }

    @Transactional
    public BookingResponse updateBookingById(BookingRequest.UpdateBookingRequest updateBookingRequest) {

        if (DAYS.between(updateBookingRequest.getArrivalDate(), updateBookingRequest.getDepartureDate()) > 3) {
            throw new MaximumBookingDayLimitExceedException("Exceeded number of days");
        }

        Booking updatedBooking;
        Optional<Booking> optionalBooking = bookingDao.findById(updateBookingRequest.getBookingId());
        Booking booking = optionalBooking.get();

        List<CampsiteBookedDate> allBookedDates = campsiteBookedDateDao.findAll();
        List<CampsiteBookedDate> currentBookedDates = campsiteBookedDateDao.getByBookingId(booking.getId());
        allBookedDates.removeAll(currentBookedDates);

        if (checkAvailability(updateBookingRequest.getArrivalDate(),
                updateBookingRequest.getDepartureDate(), allBookedDates)) {

            updatedBooking = BookingMapper.updateBookingRequestToBooking(booking, updateBookingRequest);
            bookingDao.save(updatedBooking);
            List<CampsiteBookedDate> updatedCampsiteBookedDates = updateCampsiteBookedDate(currentBookedDates, updateBookingRequest);
            campsiteBookedDateDao.saveAll(updatedCampsiteBookedDates);
        } else {
            throw new AlreadyBookedException("Already Booked");
        }
        return BookingMapper.bookingToBookingResponse(updatedBooking);
    }

    private List<CampsiteBookedDate> updateCampsiteBookedDate(List<CampsiteBookedDate> campsiteBookedDates,
                                                              BookingRequest.UpdateBookingRequest updateBookingRequest) {

        LocalDate arrivalDate = updateBookingRequest.getArrivalDate();
        LocalDate departureDate = updateBookingRequest.getDepartureDate();

        for (CampsiteBookedDate campsiteBookedDate : campsiteBookedDates) {
            if(arrivalDate.isBefore(departureDate)) {
                campsiteBookedDate.setBookedDate(arrivalDate);
                arrivalDate = arrivalDate.plusDays(1);
            }
        }
        return campsiteBookedDates;
    }

    public DateAvailabilityResponse findAvailability(LocalDate startDate, LocalDate endDate) {

        if(startDate == null) {
            startDate =LocalDate.now();
        }
        if(endDate == null) {
            endDate = startDate.plusMonths(1);
        }

        List<CampsiteBookedDate> campsiteBookedDates = campsiteBookedDateDao.findAll();
        HashSet<LocalDate> bookedDates = (HashSet<LocalDate>) campsiteBookedDates.stream()
                .map(CampsiteBookedDate::getBookedDate).collect(Collectors.toSet());
        Set<LocalDate> availableDates = new HashSet<>();
        endDate = endDate.plusDays(1);
        while(startDate.isBefore(endDate)) {
            if (!bookedDates.contains(startDate)) {
                availableDates.add(startDate);
            }
            startDate = startDate.plusDays(1);
        }
        return DateAvailabilityMapper.createDateAvailabilityResponse(availableDates);
    }

    private List<CampsiteBookedDate> createCampsiteBookedDate(BookingRequest.CreateBookingRequest createBookingRequest,
    Booking booking) {

        List<CampsiteBookedDate> campsiteBookedDates = new ArrayList<>();
        LocalDate arrivalDate = createBookingRequest.getArrivalDate();
        LocalDate departureDate = createBookingRequest.getDepartureDate();

        while(arrivalDate.isBefore(departureDate)) {
            CampsiteBookedDate campsiteBookedDate = new CampsiteBookedDate();
            campsiteBookedDate.setBookedDate(arrivalDate);
            campsiteBookedDate.setBooking(booking);
            campsiteBookedDates.add(campsiteBookedDate);
            arrivalDate = arrivalDate.plusDays(1);
        }
        return campsiteBookedDates;
    }

    private boolean checkAvailability(LocalDate arrivalDate, LocalDate departureDate,
                                      List<CampsiteBookedDate> campsiteBookedDates) {

        HashSet<LocalDate> bookedDates = (HashSet<LocalDate>) campsiteBookedDates.stream()
                .map(CampsiteBookedDate::getBookedDate).collect(Collectors.toSet());
        while(arrivalDate.isBefore(departureDate)) {
            if(bookedDates.contains(arrivalDate)) {
                return false;
            }
            arrivalDate = arrivalDate.plusDays(1);
        }
        return true;
    }
}

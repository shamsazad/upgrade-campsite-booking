package com.upgrade.campsitereservation.service;

import com.upgrade.campsitereservation.dao.IBookingDao;
import com.upgrade.campsitereservation.dao.ICampsiteBookedDateDao;
import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.dto.response.BookingResponse;
import com.upgrade.campsitereservation.entity.dto.response.DateAvailabilityResponse;
import com.upgrade.campsitereservation.exception.AlreadyBookedException;
import com.upgrade.campsitereservation.exception.MaximumBookingDayLimitExceedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.BadRequestException;
import java.time.LocalDate;

import static com.upgrade.campsitereservation.util.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private ICampsiteBookedDateDao campsiteBookedDateDao;

    @Mock
    private IBookingDao bookingDao;

    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookingService = new BookingService(bookingDao, campsiteBookedDateDao);
    }


    @Test
    public void bookCampsite() {

        Mockito.when(campsiteBookedDateDao.findAll()).thenReturn(createCampsiteBookingDates());
        BookingRequest.CreateBookingRequest createBookingRequest = createBookingRequest();
        BookingResponse bookingResponse = bookingService.bookCampsite(createBookingRequest);

        assertEquals(bookingResponse.getArrivalDate(),createBookingRequest.getArrivalDate());
        assertEquals(bookingResponse.getDepartureDate(), createBookingRequest.getDepartureDate());
    }

    @Test
    public void bookCampsiteWithAlreadyBookedDates() {
        assertThrows(AlreadyBookedException.class,
                () -> {
                    Mockito.when(campsiteBookedDateDao.findAll()).thenReturn(createCampsiteBookingDates());
                    BookingRequest.CreateBookingRequest createBookingRequest = createBookingRequest();
                    createBookingRequest.setArrivalDate(LocalDate.now().plusDays(1));
                    createBookingRequest.setDepartureDate(LocalDate.now().plusDays(3));
                    bookingService.bookCampsite(createBookingRequest);
                });
    }

    @Test
    public void bookCampsiteWithThreeMoreDays() {

        assertThrows(MaximumBookingDayLimitExceedException.class,
                () -> {
                    BookingRequest.CreateBookingRequest createBookingRequest = createBookingRequest();
                    createBookingRequest.setArrivalDate(LocalDate.now().plusDays(4));
                    createBookingRequest.setDepartureDate(LocalDate.now().plusDays(10));
                    bookingService.bookCampsite(createBookingRequest);
                });
    }

    @Test
    public void bookCampsiteWithDepartureDateBeforeArrivalDate() {

        BadRequestException badRequestException = assertThrows(BadRequestException.class,
                () -> {
                    BookingRequest.CreateBookingRequest createBookingRequest = createBookingRequest();
                    createBookingRequest.setArrivalDate(LocalDate.now().plusDays(4));
                    createBookingRequest.setDepartureDate(LocalDate.now().plusDays(1));
                    bookingService.bookCampsite(createBookingRequest);
                });
        assertEquals(badRequestException.getMessage(),"Departure date needs to be after arrival date.");
    }

    @Test
    public void updateBooking() {

        Mockito.when(bookingDao.findById(1)).thenReturn(createBooking());
        Mockito.when(campsiteBookedDateDao.findAll()).thenReturn(createCampsiteBookingDates());
        Mockito.when(campsiteBookedDateDao.getByBookingId(1)).thenReturn(createCampsiteBookingDatesForGivenId());
        BookingResponse bookingResponse = bookingService.updateBookingById(createUpdateRequest());

        assertEquals(bookingResponse.getArrivalDate(), createUpdateRequest().getArrivalDate());
        assertEquals(bookingResponse.getDepartureDate(), createUpdateRequest().getDepartureDate());
        assertEquals(bookingResponse.getBookingId(), 1);
    }

    @Test
    public void updateBookingWithAlreadyBookedDate() {

        Mockito.when(bookingDao.findById(1)).thenReturn(createBooking());
        Mockito.when(campsiteBookedDateDao.findAll()).thenReturn(createCampsiteBookingDates());
        Mockito.when(campsiteBookedDateDao.getByBookingId(1)).thenReturn(createCampsiteBookingDatesForGivenId());

        AlreadyBookedException alreadyBookedException = assertThrows(
                AlreadyBookedException.class,
                () -> {
                    BookingRequest.UpdateBookingRequest updateBookingRequest = createUpdateRequest();
                    updateBookingRequest.setArrivalDate(LocalDate.now().plusDays(3));
                    updateBookingRequest.setDepartureDate(LocalDate.now().plusDays(5));
                    bookingService.updateBookingById(updateBookingRequest);
                }
        );
        assertEquals(alreadyBookedException.getMessage(), "Booking Date issue");
    }

    @Test
    public void findAvailableDates() {
        LocalDate startDate = LocalDate.now().plusDays(3);
        LocalDate endDate = LocalDate.now().plusDays(10);

        Mockito.when(campsiteBookedDateDao.findAll()).thenReturn(createCampsiteBookingDates());
        DateAvailabilityResponse dateAvailabilityResponse = bookingService.findAvailability(startDate, endDate);

        assertEquals(dateAvailabilityResponse.getAvailableDates().size(), 6);
        assertTrue(dateAvailabilityResponse.getAvailableDates().contains(LocalDate.now().plusDays(10)));
    }

    @Test
    public void findById() {
        Mockito.when(bookingDao.findById(1)).thenReturn(createBooking());
        BookingResponse bookingResponse = bookingService.getBookingById(1);
        assertEquals(bookingResponse.getBookingId(), 1);
    }
}


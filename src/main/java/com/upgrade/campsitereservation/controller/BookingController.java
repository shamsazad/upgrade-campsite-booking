package com.upgrade.campsitereservation.controller;

import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.dto.response.BookingResponse;
import com.upgrade.campsitereservation.entity.dto.response.DateAvailabilityResponse;
import com.upgrade.campsitereservation.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "/{bookingId}")
    @ResponseStatus(value = HttpStatus.OK)
    public BookingResponse findBooking(@PathVariable(value = "bookingId") Integer bookingId) {
        return bookingService.getBookingById(bookingId);
    }

    @PostMapping(value = "/book-campsite")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookingResponse createBooking(@RequestBody @Valid BookingRequest.CreateBookingRequest createBookingRequest) {
        return bookingService.bookCampsite(createBookingRequest);
    }

    @GetMapping("/check-availability")
    @ResponseStatus(value = HttpStatus.OK)
    public DateAvailabilityResponse findAvailableDates(@Valid @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                                       @Valid @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return bookingService.findAvailability(startDate, endDate);
    }

    @DeleteMapping(value = "/{bookingId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteBooking(@PathVariable(value = "bookingId") Integer bookingId) {
        bookingService.deleteBookingById(bookingId);
    }

    @PostMapping(value = "/update-booking")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookingResponse updateBooking(@RequestBody @Valid BookingRequest.UpdateBookingRequest updateBookingRequest) {
        return bookingService.updateBookingById(updateBookingRequest);
    }
}

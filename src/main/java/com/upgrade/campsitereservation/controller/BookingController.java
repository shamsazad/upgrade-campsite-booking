package com.upgrade.campsitereservation.controller;

import com.upgrade.campsitereservation.entity.dto.request.BookingRequest;
import com.upgrade.campsitereservation.entity.dto.response.BookingResponse;
import com.upgrade.campsitereservation.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping(value = "/campsite-booking")
    @ResponseStatus(value = HttpStatus.CREATED)
    public BookingResponse createBooking(@RequestBody @Valid BookingRequest.CreateBookingRequest createBookingRequest) {
        return bookingService.bookCampsite(createBookingRequest);
    }
}

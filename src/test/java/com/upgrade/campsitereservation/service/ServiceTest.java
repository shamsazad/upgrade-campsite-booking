package com.upgrade.campsitereservation.service;

import com.upgrade.campsitereservation.dao.IBookingDao;
import com.upgrade.campsitereservation.dao.ICampsiteBookedDateDao;
import com.upgrade.campsitereservation.entity.model.Booking;
import com.upgrade.campsitereservation.util.TestHelper;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class ServiceTest {


    @Autowired
    private IBookingDao iBookingDao;

    @Autowired
    private ICampsiteBookedDateDao campsiteBookedDateDao;

    private BookingService bookingService;

    @BeforeEach
    public void setUp() {
        bookingService = new BookingService(iBookingDao, campsiteBookedDateDao);
    }

    @Test
    public void testConcurrency() {

        Optional<Booking> booking = TestHelper.createBooking();
        Booking nbooking = booking.get();
        Optional<Booking> booking1 = TestHelper.createBooking();
        Booking nbooking1 = booking1.get();
        nbooking1.setId(2);
        nbooking1.setArrivalDate(LocalDate.now().plusDays(4));
        nbooking1.setDepartureDate(LocalDate.now().plusDays(6));
        iBookingDao.save(booking.get());
        iBookingDao.save(booking1.get());

        Booking retrivedBooking1 = iBookingDao.getOne(nbooking1.getId());
        Booking retrivedBooking = iBookingDao.getOne(nbooking1.getId());
        retrivedBooking.setArrivalDate(LocalDate.now().plusDays(3));
        iBookingDao.saveAndFlush(retrivedBooking);
        Booking retrivedBooking2 = iBookingDao.getOne(nbooking1.getId());
        retrivedBooking1.setDepartureDate(LocalDate.now().plusDays(7));
        iBookingDao.saveAndFlush(retrivedBooking1);

        Booking fretrivedBooking1 = iBookingDao.getOne(nbooking1.getId());

        retrivedBooking.getId();
    }

    @Test
    public void testConcurrencyUsingThreads() throws InterruptedException {

        int numberOfThreads = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Optional<Booking> booking = TestHelper.createBooking();
        iBookingDao.saveAndFlush(booking.get());

        for(int i=0; i<numberOfThreads ;i++) {
            service.execute(() -> {

                Booking retrivedBooking = iBookingDao.getOne(booking.get().getId());
                iBookingDao.save(retrivedBooking);
                latch.countDown();
            });
        }
        latch.await();
        Assert.assertEquals(iBookingDao.getOne(booking.get().getId()).getArrivalDate(), LocalDateTime.now());
    }
}

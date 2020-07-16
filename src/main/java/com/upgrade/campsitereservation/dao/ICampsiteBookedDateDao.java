package com.upgrade.campsitereservation.dao;

import com.upgrade.campsitereservation.entity.model.CampsiteBookedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICampsiteBookedDateDao extends JpaRepository<CampsiteBookedDate, Integer> {
    void deleteByBookingId(Integer bookingId);

    List<CampsiteBookedDate> getByBookingId(Integer bookingId);
}

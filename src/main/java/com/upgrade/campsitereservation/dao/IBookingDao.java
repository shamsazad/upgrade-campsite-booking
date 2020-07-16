package com.upgrade.campsitereservation.dao;

import com.upgrade.campsitereservation.entity.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingDao extends JpaRepository<Booking, Integer> {
}

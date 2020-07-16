package com.upgrade.campsitereservation.entity.dto.response;

import java.time.LocalDate;
import java.util.Set;

public class DateAvailabilityResponse {
    private Set<LocalDate> availableDates;

    public Set<LocalDate> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(Set<LocalDate> availableDates) {
        this.availableDates = availableDates;
    }
}

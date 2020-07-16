package com.upgrade.campsitereservation.util;

import com.upgrade.campsitereservation.entity.dto.response.DateAvailabilityResponse;

import java.time.LocalDate;
import java.util.Set;

public class DateAvailabilityMapper {

    public static DateAvailabilityResponse createDateAvailabilityResponse(Set<LocalDate> localDateSet) {

        DateAvailabilityResponse dateAvailabilityResponse = new DateAvailabilityResponse();
        dateAvailabilityResponse.setAvailableDates(localDateSet);
        return dateAvailabilityResponse;
    }
}

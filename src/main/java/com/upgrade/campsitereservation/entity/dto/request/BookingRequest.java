package com.upgrade.campsitereservation.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.upgrade.campsitereservation.entity.BaseEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingRequest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreateBookingRequest extends BaseEntity {

        @NotNull
        private String firstName;
        @NotNull
        private String lastName;
        @NotNull
        private String email;
        @NotNull
        private LocalDate arrivalDate;
        @NotNull
        private LocalDate departureDate;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public LocalDate getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(LocalDate arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public LocalDate getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
        }
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UpdateBookingRequest {

        @NotNull
        private LocalDateTime arrivalDate;
        @NotNull
        private LocalDateTime departureDate;

        public LocalDateTime getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(LocalDateTime arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public LocalDateTime getDepartureDate() {
            return departureDate;
        }

        public void setDepartureDate(LocalDateTime departureDate) {
            this.departureDate = departureDate;
        }
    }
}

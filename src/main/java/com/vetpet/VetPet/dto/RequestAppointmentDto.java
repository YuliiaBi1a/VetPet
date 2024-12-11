package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Pet;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestAppointmentDto(
        LocalDate date,
        LocalTime time,
        String reason,
        Long petId
) {
    public Appointment toEntity(Pet pet) {
        return new Appointment(
                this.date,
                this.time,
                this.reason,
                pet
        );
    }
}

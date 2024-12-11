package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Pet;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseAppointmentDto(
        Long id,
        LocalDate date,
        LocalTime time,
        String reason,
        Pet pet
) {
    public static ResponseAppointmentDto fromEntity(Appointment appointment) {
        return new ResponseAppointmentDto(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getReason(),
                appointment.getPet()
        );
    }
    
}

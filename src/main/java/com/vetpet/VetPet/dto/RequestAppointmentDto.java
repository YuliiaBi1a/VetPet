package com.vetpet.VetPet.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestAppointmentDto(
        LocalDate date,
        LocalTime time,
        String reason,
        Long petId
) {}

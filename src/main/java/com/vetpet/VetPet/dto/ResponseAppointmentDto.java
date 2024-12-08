package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Pet;

import java.time.LocalDate;
import java.time.LocalTime;

public record ResponseAppointmentDto(
        Long id,
        LocalDate date,
        LocalTime time,
        String reason,
        Pet pet
) {}

package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestAppointmentDto(

        @NotNull(message = "Date cannot be null")
        LocalDate date,

        @NotNull(message = "Time cannot be null")
        LocalTime time,

        @NotBlank(message = "Reason cannot be blank")
        @Size(max = 255, message = "Reason must be less than 255 characters")
        @Pattern(regexp = "^[a-zA-Z\\s\\p{Punct}]+$", message = "Reason must contain only letters and spaces")
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

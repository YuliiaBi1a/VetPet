package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Guardian;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;


public record RequestGuardianDto(
        @NotBlank(message = "Name can not be empty")
        @Size(min = 3, max = 20, message = "Name must contains 3 to 20 letters")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
        String name,

        @Email(message = "Email format is not correct")
        String email,

        @NotBlank(message = "Address can not be empty")
        String address,

        @NotBlank(message = "Phone can not be empty")
        @Size(min = 9, max = 9, message = "Phone must have 9 digits")
        @Pattern(regexp = "\\d{9}", message = "Phone must have 9 digits, musn't have letters")
        String phone) {

    public Guardian toEntity() {

        return new Guardian(

                this.name,
                this.email,
                this.phone,
                this.address
        );
    }
}

package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Guardian;
import jakarta.validation.constraints.*;

public record RequestPetDto (
        @NotBlank(message = "Name can not be empty")
        @Size( max = 20, message = "Name must have less than 20 letters")
        String name,

        @NotNull(message = "Age can not be empty")
        @Max(value = 100, message = "The age must be less than or equal to 100")
        int age,

        //TODO        @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Breed musn't have numbers")
        String breed,

        @NotBlank(message = "Name can not be empty")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Specie musn't have numbers")
        String class_species,

        Long guardianId) {
public Pet toEntity(Guardian guardian){
    return new Pet(this.name, this.age, this.breed, this.class_species, guardian);
}
}

package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.entity.Pet;

public record ResponsePetDto(Long id, String name, int age, String breed, String species, Guardian guardian) {
    public static ResponsePetDto fromEntity(Pet pet) {
        return new ResponsePetDto(
                pet.getId(),
                pet.getName(),
                pet.getAge(),
                pet.getBreed(),
                pet.getClass_species(),
                pet.getGuardian()
        );
    }
}

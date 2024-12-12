package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Guardian;

public record RequestPetDto (String name, int age, String breed, String species, Long guardianId) {
public Pet toEntity(Guardian guardian){
    return new Pet(this.name, this.age, this.breed, this.species, guardian);
}
}

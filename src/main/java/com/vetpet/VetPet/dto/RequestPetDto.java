package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Tutor;

public record RequestPetDto (String name, int age, String breed, String class_species, Long tutorId) {
public Pet toEntity(Tutor tutor){
    return new Pet(this.name, this.age, this.breed, this.class_species, tutor);
}
}

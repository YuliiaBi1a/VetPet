package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Tutor;

public record RequestPetDto (String name, int age, String breed, String class_species, Long tutorId) {
}

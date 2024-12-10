package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Guardian;

public record ResponsePetDto(Long id, String name, int age, String breed, String class_species, Guardian guardian) {
}

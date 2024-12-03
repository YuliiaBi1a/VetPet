package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Tutor;

public record ResponsePetDto(Long id, String name, int age, String breed, String class_species, Tutor tutor) {
}

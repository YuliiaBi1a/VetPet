package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Tutor;

public record RequestTutorDto(String name, String surname, int phoneNumber) {
    //Mapper
    public Tutor toEntity() {
        return new Tutor(
                this.name,
                this.surname,
                this.phoneNumber
        );
    }
}

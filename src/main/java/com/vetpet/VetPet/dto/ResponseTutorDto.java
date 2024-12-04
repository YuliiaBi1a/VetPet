package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Tutor;

public record ResponseTutorDto(Long id, String name, String surname, int phoneNumber) {

    public static ResponseTutorDto fromEntity(Tutor tutor) {
        return new ResponseTutorDto(
                tutor.getId(),
                tutor.getName(),
                tutor.getSurname(),
                tutor.getPhoneNumber()
        );
    }
}

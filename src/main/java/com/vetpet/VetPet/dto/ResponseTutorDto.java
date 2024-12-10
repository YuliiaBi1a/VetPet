package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Guardian;

public record ResponseTutorDto(Long id, String name, String email, String phone, String address) {

    public static ResponseTutorDto fromEntity(Guardian guardian) {
        return new ResponseTutorDto(
                guardian.getId(),
                guardian.getName(),
                guardian.getEmail(),
                guardian.getPhone(),
                guardian.getAddress()
        );
    }
}

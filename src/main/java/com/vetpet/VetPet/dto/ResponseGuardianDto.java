package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Guardian;

public record ResponseGuardianDto(Long id, String name, String email, String phone, String address) {

    public static ResponseGuardianDto fromEntity(Guardian guardian) {
        return new ResponseGuardianDto(
                guardian.getId(),
                guardian.getName(),
                guardian.getEmail(),
                guardian.getPhone(),
                guardian.getAddress()
        );
    }
}

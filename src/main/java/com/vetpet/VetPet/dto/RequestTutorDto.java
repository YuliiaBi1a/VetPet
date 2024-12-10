package com.vetpet.VetPet.dto;

import com.vetpet.VetPet.entity.Guardian;

public record RequestTutorDto(String name, String email, String phone, String address) {

    public Guardian toEntity() {
        return new Guardian(
                this.name,
                this.email,
                this.phone,
                this.address
        );
    }
}

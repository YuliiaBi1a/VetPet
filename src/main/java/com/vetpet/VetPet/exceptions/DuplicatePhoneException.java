package com.vetpet.VetPet.exceptions;

public class DuplicatePhoneException extends AppException {

    public DuplicatePhoneException(String phone) {
        super("Phone number " + phone + " already exists");
    }
}

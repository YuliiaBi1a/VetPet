package com.vetpet.VetPet.exceptions;

public class DuplicatePhoneException extends AppException {

    public DuplicatePhoneException(int phoneNumber) {
        super("Phone number " + phoneNumber + " already exists");
    }
}

package com.vetpet.VetPet.exceptions;

public class DuplicatePhoneException extends AppException {

    public DuplicatePhoneException(int phoneNumber) {
        super("Employee with DNI " + phoneNumber + " already exists");
    }
}

package com.vetpet.VetPet.exceptions;

public class NoIdFoundException extends AppException {

    public NoIdFoundException(Long id) {
        super("Employee with DNI " + id + " not found");
    }
}

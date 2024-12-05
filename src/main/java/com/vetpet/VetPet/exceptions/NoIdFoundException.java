package com.vetpet.VetPet.exceptions;

public class NoIdFoundException extends AppException {

    public NoIdFoundException(Long id) {
        super("Entity with ID " + id + " not found in data base");
    }
}
package com.vetpet.VetPet.exceptions;

public class NoIdFoundBadRequestException extends AppException {


    public NoIdFoundBadRequestException(Long id) {
        super("The provided ID " + id + " does not match any existing Tutor records");
    }
}

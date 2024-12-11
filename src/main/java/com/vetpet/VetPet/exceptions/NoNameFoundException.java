package com.vetpet.VetPet.exceptions;

public class NoNameFoundException extends AppException {
    public NoNameFoundException(String name) {
        super("Guardian with name " + name + " not found in data base");
    }
}

package com.vetpet.VetPet.exceptions;

public class NoPetsFoundException extends RuntimeException {
    public NoPetsFoundException(Long guardianId) {
        super("No pets found for guardian with ID: " + guardianId);
    }
}

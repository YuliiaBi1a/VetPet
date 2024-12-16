package com.vetpet.VetPet.exceptions;

public class DependencyException extends AppException{
    public DependencyException(Long id) {
        super("Cannot delete entity with id " + id + " because it has associated registers.");
    }
}

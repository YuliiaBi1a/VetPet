package com.vetpet.VetPet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatePhoneException.class)
    public ResponseEntity<String> handleDuplicatePhoneNumber(DuplicatePhoneException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoRegistersFoundException.class)
    public ResponseEntity<String> handleNotFoundRegisters(NoRegistersFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(NoIdFoundException.class)
    public ResponseEntity<String> handleNotFoundId(NoIdFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(NoIdFoundBadRequestException.class)
    public ResponseEntity<String> handleBadRequest(NoIdFoundBadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}


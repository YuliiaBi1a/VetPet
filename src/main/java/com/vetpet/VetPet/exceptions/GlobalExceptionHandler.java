package com.vetpet.VetPet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(DependencyException.class)
    public ResponseEntity<String> handleDependencyExist(DependencyException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(NoNameFoundException.class)
    public ResponseEntity<String> handleNotFoundName(NoNameFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        for(FieldError error: exception.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }


}

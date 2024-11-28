package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutors")

public class TutorController {
    private final TutorRepository TUTOR_REPOSITORY;

    public TutorController(TutorRepository tutorRepository) {
        TUTOR_REPOSITORY = tutorRepository;

    }

    @GetMapping
    public List<Tutor> getTutorsList() {
        return TUTOR_REPOSITORY.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTutorById(@PathVariable Long id){
        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findById(id);
        if(optionalTutor.isEmpty()){
            return new ResponseEntity<>("El tutor con " + id +" actualmente no se encuentra asociado a un tutor registrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalTutor.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTutor(@RequestBody Tutor tutor){
        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findByPhoneNumber(tutor.getPhoneNumber());
        if (optionalTutor.isPresent()){
            return new ResponseEntity<>( "Este teléfono ya esta asociado a un tutor existente. Pertenece a " + tutor.getName() + " " + tutor.getSurname(), HttpStatus.CONFLICT);
        }
        Tutor newTutor = TUTOR_REPOSITORY.save(tutor);
        return new ResponseEntity<>(newTutor,HttpStatus.CREATED);
    }


}


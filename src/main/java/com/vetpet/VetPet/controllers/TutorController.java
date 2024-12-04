package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import com.vetpet.VetPet.services.TutorServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tutors")

public class TutorController {

    private final TutorServices TUTOR_SERVICES;
    public TutorController(TutorServices tutorServices) {
        TUTOR_SERVICES = tutorServices;
    }

    @PostMapping
    public ResponseEntity<?> saveNewTutor(@RequestBody RequestTutorDto requestTutor){
        Tutor newTutor = TUTOR_SERVICES.createTutor(requestTutor);
        return new ResponseEntity<>(newTutor,HttpStatus.CREATED);
    }

    @GetMapping
    public List<Tutor> getTutorsList() {
        return TUTOR_SERVICES.findAllTutor();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTutorById(@PathVariable Long id){
        return new ResponseEntity<>(TUTOR_SERVICES.findTutorById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTutorById(@PathVariable Long id, @RequestBody RequestTutorDto request) {
        return new ResponseEntity<>(TUTOR_SERVICES.updateTutor(id, request) ,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTutorById (@PathVariable Long id){
        TUTOR_SERVICES.deleteTutorById(id);
        return new ResponseEntity<>(" Tutor has been deleted.",HttpStatus.NO_CONTENT);
    }
}


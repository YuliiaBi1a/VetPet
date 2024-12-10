package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.services.GuardianServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardian")

public class GuardianController {

    private final GuardianServices TUTOR_SERVICES;
    public GuardianController(GuardianServices guardianServices) {
        TUTOR_SERVICES = guardianServices;
    }

    @PostMapping
    public ResponseEntity<?> saveNewTutor(@RequestBody RequestTutorDto requestTutor){
        Guardian newGuardian = TUTOR_SERVICES.createTutor(requestTutor);
        return new ResponseEntity<>(newGuardian,HttpStatus.CREATED);
    }

    @GetMapping
    public List<Guardian> getTutorsList() {
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


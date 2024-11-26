package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

 @RestController
 @RequestMapping("/api/tutors")

public class TutorController {
    private final TutorRepository TUTOR_REPOSITORY;

    public TutorController(TutorRepository tutorRepository) {
        TUTOR_REPOSITORY = tutorRepository;

    }
    @GetMapping
    public List<Tutor> GetTutorsList(){
        return TUTOR_REPOSITORY.findAll();
    }
}

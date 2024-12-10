package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestPetDto;

import com.vetpet.VetPet.entity.Pet;

import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import com.vetpet.VetPet.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pets")
public class PetController {
    private final PetRepository PET_REPOSITORY;
    private final GuardianRepository GUARDIAN_REPOSITORY;
    private final PetService PET_SERVICES;

    public PetController(PetRepository petRepository, GuardianRepository guardianRepository, PetService petServices) {
        PET_REPOSITORY = petRepository;
        GUARDIAN_REPOSITORY = guardianRepository;
        PET_SERVICES = petServices;
    }

    @GetMapping
    public ResponseEntity<?> getPetList() {
        return new ResponseEntity<>(PET_SERVICES.findAllPets(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Long id) {
        return new ResponseEntity<>(PET_SERVICES.findPetById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody RequestPetDto requestPetDto) {
        Pet newPet = PET_SERVICES.createPet(requestPetDto);
        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Long id, @RequestBody RequestPetDto petDto) {
        return new ResponseEntity<>(PET_SERVICES.updatePet(id, petDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePetById(@PathVariable Long id) {
        PET_SERVICES.deletePetById(id);
        return new ResponseEntity<>("Pet has been deleted.", HttpStatus.NO_CONTENT);
    }
}

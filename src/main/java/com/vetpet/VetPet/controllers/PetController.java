package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestPetDto;

import com.vetpet.VetPet.dto.ResponseAppointmentDto;
import com.vetpet.VetPet.dto.ResponseGuardianDto;
import com.vetpet.VetPet.dto.ResponsePetDto;
import com.vetpet.VetPet.entity.Pet;

import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import com.vetpet.VetPet.services.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ResponsePetDto>> getPetList() {
        List<ResponsePetDto> pets = PET_SERVICES.findAllPets();
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePetDto> getPetById(@PathVariable Long id) {
        ResponsePetDto pet = PET_SERVICES.findPetById(id);
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @GetMapping(params = "guardianId")
    public ResponseEntity<List<ResponsePetDto>> getPetsByGuardianId(@RequestParam Long guardianId) {
        List<ResponsePetDto> pets = PET_SERVICES.findPetsByGuardianId(guardianId);
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponsePetDto> createPet(@RequestBody RequestPetDto requestPet) {
        ResponsePetDto newPet = PET_SERVICES.createPet(requestPet);
        return new ResponseEntity<>(newPet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePetDto> updatePet(@PathVariable Long id, @RequestBody RequestPetDto request) {
        ResponsePetDto updatePet = PET_SERVICES.updatePet(id, request);
        return new ResponseEntity<>(updatePet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePetById(@PathVariable Long id) {
        PET_SERVICES.deletePetById(id);
        return new ResponseEntity<>("Pet has been deleted.", HttpStatus.OK);
    }
}

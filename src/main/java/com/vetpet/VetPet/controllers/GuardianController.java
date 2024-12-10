package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.dto.ResponseTutorDto;
import com.vetpet.VetPet.services.GuardianServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guardians")
public class GuardianController {

    private final GuardianServices GUARDIAN_SERVICES;

    public GuardianController(GuardianServices guardianServices) {
        GUARDIAN_SERVICES = guardianServices;
    }

    @PostMapping
    public ResponseEntity<ResponseTutorDto> saveNewGuardian(@RequestBody RequestTutorDto requestTutor) {
        ResponseTutorDto newGuardian = GUARDIAN_SERVICES.createGuardian(requestTutor);
        return new ResponseEntity<>(newGuardian, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTutorDto>> getTutorsList() {
        List<ResponseTutorDto> guardians = GUARDIAN_SERVICES.findAllGuardians();
        return new ResponseEntity<>(guardians, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTutorDto> getTutorById(@PathVariable Long id) {
        ResponseTutorDto guardian = GUARDIAN_SERVICES.findGuardianById(id);
        return new ResponseEntity<>(guardian, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTutorDto> updateTutorById(@PathVariable Long id, @RequestBody RequestTutorDto request) {
        ResponseTutorDto updatedGuardian = GUARDIAN_SERVICES.updateGuardian(id, request);
        return new ResponseEntity<>(updatedGuardian, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTutorById(@PathVariable Long id) {
        GUARDIAN_SERVICES.deleteGuardianById(id);
        return new ResponseEntity<>("Tutor has been deleted.", HttpStatus.NO_CONTENT);
    }
}

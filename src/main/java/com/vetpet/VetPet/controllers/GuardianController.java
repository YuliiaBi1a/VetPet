package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestGuardianDto;
import com.vetpet.VetPet.dto.ResponseGuardianDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.services.GuardianServices;
import jakarta.websocket.server.PathParam;
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
    public ResponseEntity<ResponseGuardianDto> saveNewGuardian(@RequestBody RequestGuardianDto requestGuardian) {
        ResponseGuardianDto newGuardian = GUARDIAN_SERVICES.createGuardian(requestGuardian);
        return new ResponseEntity<>(newGuardian, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseGuardianDto>> getGuardianList(@PathParam("name") String name) {
       if(name == null){
        List<ResponseGuardianDto> guardians = GUARDIAN_SERVICES.findAllGuardians();
        return new ResponseEntity<>(guardians, HttpStatus.OK);
       }
        List<ResponseGuardianDto> searchGuardians = GUARDIAN_SERVICES.searchByName(name);
       return new ResponseEntity<>(searchGuardians, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGuardianDto> getGuardianById(@PathVariable Long id) {
        ResponseGuardianDto guardian = GUARDIAN_SERVICES.findGuardianById(id);
        return new ResponseEntity<>(guardian, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGuardianDto> updateGuardianById(@PathVariable Long id, @RequestBody RequestGuardianDto request) {
        ResponseGuardianDto updatedGuardian = GUARDIAN_SERVICES.updateGuardian(id, request);
        return new ResponseEntity<>(updatedGuardian, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGuardianById(@PathVariable Long id) {
        GUARDIAN_SERVICES.deleteGuardianById(id);
        return new ResponseEntity<>("Guardian has been deleted.", HttpStatus.OK);
    }
}

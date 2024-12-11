package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestGuardianDto;
import com.vetpet.VetPet.dto.ResponseGuardianDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.exceptions.*;
import com.vetpet.VetPet.repository.GuardianRepository;
import com.vetpet.VetPet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuardianServices {

    private final GuardianRepository GUARDIAN_REPOSITORY;
    private final PetRepository PET_REPOSITORY;

    public GuardianServices(GuardianRepository guardianRepository, PetRepository petRepository) {
        this.GUARDIAN_REPOSITORY = guardianRepository;
        this.PET_REPOSITORY = petRepository;
    }

    // POST
    public ResponseGuardianDto createGuardian(RequestGuardianDto guardianDto) {
        Optional<Guardian> existingGuardian = GUARDIAN_REPOSITORY.findByPhone(guardianDto.phone());
        if (existingGuardian.isPresent()) {
            throw new DuplicatePhoneException(guardianDto.phone());
        }

        Guardian newGuardian = guardianDto.toEntity();
        Guardian savedGuardian = GUARDIAN_REPOSITORY.save(newGuardian);
        return ResponseGuardianDto.fromEntity(savedGuardian);
    }

    // GET: Get all guardians
    public List<ResponseGuardianDto> findAllGuardians() {
        List<Guardian> guardians = GUARDIAN_REPOSITORY.findAll();
        if (guardians.isEmpty()) {
            throw new NoRegistersFoundException();
        }

        return guardians.stream()
                .map(ResponseGuardianDto::fromEntity)
                .collect(Collectors.toList());
    }

    // GET: Find guardian by ID
    public ResponseGuardianDto findGuardianById(Long id) {
        Guardian guardian = GUARDIAN_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        return ResponseGuardianDto.fromEntity(guardian);
    }

    // GET: search guardian like name
    public List<ResponseGuardianDto> searchByName(String name) {
        List<Guardian> guardianList = GUARDIAN_REPOSITORY.findLikeName(name);
        if (guardianList.isEmpty()) {
            throw new NoNameFoundException(name);
        }
        return guardianList.stream()
                .map(ResponseGuardianDto::fromEntity).toList();
    }

    // PUT: Update guardian
    public ResponseGuardianDto updateGuardian(Long id, RequestGuardianDto request) {
        Guardian existingGuardian = GUARDIAN_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));

        // Update fields
        existingGuardian.setName(request.name());
        existingGuardian.setEmail(request.email());
        existingGuardian.setPhone(request.phone());
        existingGuardian.setAddress(request.address());

        Guardian updatedGuardian = GUARDIAN_REPOSITORY.save(existingGuardian);
        return ResponseGuardianDto.fromEntity(updatedGuardian);
    }

    // DELETE: Delete guardian by ID si no tiene dependencias de Pet
    public void deleteGuardianById(Long id) {
        Guardian guardian = GUARDIAN_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));

        boolean hasPets = PET_REPOSITORY.existsByGuardianId(id);
        if (hasPets) {
            throw new DependencyException(id);
        }

        GUARDIAN_REPOSITORY.deleteById(id);
    }
}

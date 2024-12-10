package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.dto.ResponseTutorDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.exceptions.DuplicatePhoneException;
import com.vetpet.VetPet.exceptions.NoIdFoundException;
import com.vetpet.VetPet.exceptions.NoRegistersFoundException;
import com.vetpet.VetPet.repository.GuardianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuardianServices {

    private final GuardianRepository GUARDIAN_REPOSITORY;

    public GuardianServices(GuardianRepository guardianRepository) {
        this.GUARDIAN_REPOSITORY = guardianRepository;
    }

    // POST
    public ResponseTutorDto createTutor(RequestTutorDto tutorDto) {
        Optional<Guardian> existingTutor = GUARDIAN_REPOSITORY.findByPhone(tutorDto.phone());
        if (existingTutor.isPresent()) {
            throw new DuplicatePhoneException(tutorDto.phone());
        }

        Guardian newGuardian = tutorDto.toEntity();
        Guardian savedGuardian = GUARDIAN_REPOSITORY.save(newGuardian);
        return ResponseTutorDto.fromEntity(savedGuardian);
    }

    // GET: Get all tutors
    public List<ResponseTutorDto> findAllTutors() {
        List<Guardian> guardians = GUARDIAN_REPOSITORY.findAll();
        if (guardians.isEmpty()) {
            throw new NoRegistersFoundException();
        }

        return guardians.stream()
                .map(ResponseTutorDto::fromEntity)
                .collect(Collectors.toList());
    }

    // GET: Find tutor by ID
    public ResponseTutorDto findTutorById(Long id) {
        Guardian guardian = GUARDIAN_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        return ResponseTutorDto.fromEntity(guardian);
    }

    // PUT: Update tutor
    public ResponseTutorDto updateTutor(Long id, RequestTutorDto request) {
        Guardian existingGuardian = GUARDIAN_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));

        // Update fields
        existingGuardian.setName(request.name());
        existingGuardian.setEmail(request.email());
        existingGuardian.setPhone(request.phone());
        existingGuardian.setAddress(request.address());

        Guardian updatedGuardian = GUARDIAN_REPOSITORY.save(existingGuardian);
        return ResponseTutorDto.fromEntity(updatedGuardian);
    }

    // DELETE: Delete tutor by ID
    public void deleteTutorById(Long id) {
        Guardian guardian = GUARDIAN_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        GUARDIAN_REPOSITORY.deleteById(id);
    }
}

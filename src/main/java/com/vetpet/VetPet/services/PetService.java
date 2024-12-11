package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.dto.ResponseGuardianDto;
import com.vetpet.VetPet.dto.ResponsePetDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.exceptions.NoIdFoundBadRequestException;
import com.vetpet.VetPet.exceptions.NoIdFoundException;
import com.vetpet.VetPet.exceptions.NoRegistersFoundException;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository PET_REPOSITORY;

    private final GuardianRepository GUARDIAN_REPOSITORY;

    public PetService(PetRepository petRepository, GuardianRepository guardianRepository) {
        PET_REPOSITORY = petRepository;
        GUARDIAN_REPOSITORY = guardianRepository;
    }

    //POST
    public ResponsePetDto createPet(RequestPetDto petDto) {
        Guardian guardian = GUARDIAN_REPOSITORY.findById(petDto.guardianId())
                .orElseThrow(() -> new NoIdFoundBadRequestException(petDto.guardianId()));

        Pet newPet = petDto.toEntity(guardian);
        Pet savedPet = PET_REPOSITORY.save(newPet);
        return ResponsePetDto.fromEntity(savedPet);
    }

    //GET
    public List<ResponsePetDto> findAllPets() {
        List<Pet> pets = PET_REPOSITORY.findAll();
        if (pets.isEmpty()) {
            throw new NoRegistersFoundException();
        }
        return pets.stream()
                .map(ResponsePetDto::fromEntity)
                .toList();
    }

    //GET
    public ResponsePetDto findPetById(Long id) {
        Pet pet = PET_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        return ResponsePetDto.fromEntity(pet);
    }

    //PUT
    public ResponsePetDto updatePet(Long id, RequestPetDto request) {
        Pet existingPet = PET_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));

        Guardian guardian = GUARDIAN_REPOSITORY.findById(request.guardianId())
                .orElseThrow(() -> new NoIdFoundBadRequestException(request.guardianId()));

        existingPet.setName(request.name());
        existingPet.setAge(request.age());
        existingPet.setBreed(request.breed());
        existingPet.setClass_species(request.class_species());
        existingPet.setGuardian(guardian);

        Pet updatedPet = PET_REPOSITORY.save(existingPet);
        return ResponsePetDto.fromEntity(updatedPet);
    }

    //DELETE
    public void deletePetById(Long id){
        Pet pet = PET_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        PET_REPOSITORY.deleteById(id);
    }
}

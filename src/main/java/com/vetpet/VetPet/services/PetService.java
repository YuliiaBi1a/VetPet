package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestPetDto;
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

    public Pet createPet(RequestPetDto petDto) {
        Guardian guardian = GUARDIAN_REPOSITORY.findById(petDto.guardianId())
                .orElseThrow(() -> new NoIdFoundBadRequestException(petDto.guardianId()));

        Pet newPet = petDto.toEntity(guardian);
        return PET_REPOSITORY.save(newPet);
    }

    public List<Pet> findAllPets() {
        List<Pet> pets = PET_REPOSITORY.findAll();
        if (pets.isEmpty()) {
            throw new NoRegistersFoundException();
        }
        return pets;
    }

    public Pet findPetById(Long id) {
        return PET_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
    }

    public Pet updatePet(Long id, RequestPetDto petDto) {
        Optional<Pet> optionalPet = PET_REPOSITORY.findById(id);
        if (optionalPet.isEmpty()) {
            throw new NoIdFoundException(id);
        }

        Optional<Guardian> optionalGuardian = GUARDIAN_REPOSITORY.findById(petDto.guardianId());
        if (optionalGuardian.isEmpty()) {
            throw new NoIdFoundBadRequestException(petDto.guardianId());
        }
        Pet existingPet = optionalPet.get();

        existingPet.setName(petDto.name());
        existingPet.setAge(petDto.age());
        existingPet.setBreed(petDto.breed());
        existingPet.setClass_species(petDto.class_species());
        existingPet.setGuardian(optionalGuardian.get());

        return PET_REPOSITORY.save(existingPet);
    }

    public void deletePetById(Long id){
        Optional<Pet> optionalPet = PET_REPOSITORY.findById(id);
        if (optionalPet.isEmpty()){
            throw new NoIdFoundException(id);
        }
        PET_REPOSITORY.deleteById(id);
    }
}

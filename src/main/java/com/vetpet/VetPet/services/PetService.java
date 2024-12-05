package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.TutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository PET_REPOSITORY;

    private final TutorRepository TUTOR_REPOSITORY;

    public PetService(PetRepository petRepository, TutorRepository tutorRepository) {
        PET_REPOSITORY = petRepository;
        TUTOR_REPOSITORY = tutorRepository;
    }

    public Pet createPet(RequestPetDto petDto) {
        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findById(petDto.tutorId());
        if (optionalTutor.isEmpty()) {
            throw new RuntimeException("tutor with" + petDto.tutorId() + " doesn't exist");
        }

        Pet newPet = new Pet(petDto.name(),
                petDto.age(),
                petDto.breed(),
                petDto.class_species(),
                optionalTutor.get());
        return PET_REPOSITORY.save(newPet);

    }
    public List<Pet> findAllPets() {
        List<Pet> pets = PET_REPOSITORY.findAll();
        if (pets.isEmpty()) {
            throw new RuntimeException("Not found"); //TODO customizar exception
        }
        return pets;
    }
    public Pet findPetById(Long id) {
        return PET_REPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet is not found with ID: " + id));
    }


}

package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.exceptions.NoIdFoundBadRequestException;
import com.vetpet.VetPet.exceptions.NoIdFoundException;
import com.vetpet.VetPet.exceptions.NoRegistersFoundException;
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
        Tutor tutor = TUTOR_REPOSITORY.findById(petDto.tutorId())
                .orElseThrow(() -> new NoIdFoundBadRequestException(petDto.tutorId()));

        Pet newPet = petDto.toEntity(tutor);
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

        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findById(petDto.tutorId());
        if (optionalTutor.isEmpty()) {
            throw new NoIdFoundBadRequestException(petDto.tutorId());
        }
        Pet existingPet = optionalPet.get();

        existingPet.setName(petDto.name());
        existingPet.setAge(petDto.age());
        existingPet.setBreed(petDto.breed());
        existingPet.setClass_species(petDto.class_species());
        existingPet.setTutor(optionalTutor.get());

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

package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.TutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pets")


public class PetController {
    private final PetRepository PET_REPOSITORY;
    private final TutorRepository TUTOR_REPOSITORY;

    public PetController(PetRepository petRepository, TutorRepository tutorRepository) {
        PET_REPOSITORY = petRepository;
        TUTOR_REPOSITORY = tutorRepository;

    }


    @GetMapping
    public List<Pet> getPetList() {
        return PET_REPOSITORY.findAll();

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Long id){
        Optional<Pet> optionalPet = PET_REPOSITORY.findById(id);
        if(optionalPet.isEmpty()){
            return new ResponseEntity<>("the pet with " + id +" doesnt match with any pet currently", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalPet.get(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody RequestPetDto requestPetDto){
        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findById(requestPetDto.tutorId());
        if (optionalTutor.isEmpty()){
           return new ResponseEntity<>( "tutor with" +  requestPetDto.tutorId() + " doesn't exist", HttpStatus.NOT_FOUND);
        }

        Pet newPet = new Pet(requestPetDto.name(),
                requestPetDto.age(),
                requestPetDto.breed(),
                requestPetDto.class_species(),
                optionalTutor.get());
         PET_REPOSITORY.save(newPet);
        return new ResponseEntity<>(newPet,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePetById(@PathVariable Long id, @RequestBody Pet updatePet) {
        Optional<Pet> optionalPet = PET_REPOSITORY.findById(id);
        if (optionalPet.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pet basepet = optionalPet.get();
        basepet.setName(updatePet.getName());
        basepet.setAge(updatePet.getAge());
        basepet.setBreed(updatePet.getBreed());
        basepet.setClass_species(updatePet.getClass_species());
        basepet.setTutor(updatePet.getTutor());

        PET_REPOSITORY.save(basepet);
        return new ResponseEntity<>(" Pet has been updated." ,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePetById (@PathVariable Long id){
        Optional<Pet> optionalPet = PET_REPOSITORY.findById(id);
        if (optionalPet.isEmpty()) {
            return new ResponseEntity<>("The id " + id + " isn´t associated with any pet currently.",HttpStatus.NOT_FOUND);
        }
        PET_REPOSITORY.deleteById(id);
        return new ResponseEntity<>(" Pet has been deleted.",HttpStatus.OK);

    }
}

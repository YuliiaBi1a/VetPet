package com.vetpet.VetPet.controllers;

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

    public PetController(PetRepository petRepository) {
        PET_REPOSITORY = petRepository;


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
    public ResponseEntity<?> createPet(@RequestBody Pet pet){
        Optional<Pet> optionalPet = PET_REPOSITORY.findByName(pet.getName());
        if (optionalPet.isPresent()){
            return new ResponseEntity<>( "this name is already asociated with another pet " + pet.getName()  , HttpStatus.CONFLICT);
        }
        Pet newPet = PET_REPOSITORY.save(pet);
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

package com.vetpet.VetPet.repository;

import com.vetpet.VetPet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet,Long> {
    Optional<Pet> findByName(String name);
}

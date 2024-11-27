package com.vetpet.VetPet.repository;

import com.vetpet.VetPet.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository <Tutor,Long>{
    Optional<Tutor> findByName(String name);

    Optional<Tutor> findByPhoneNumber(int phoneNumber);
}


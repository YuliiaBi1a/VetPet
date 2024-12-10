package com.vetpet.VetPet.repository;

import com.vetpet.VetPet.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository <Guardian,Long>{
    Optional<Guardian> findByName(String name);

    Optional<Guardian> findByPhone(String phone);
}


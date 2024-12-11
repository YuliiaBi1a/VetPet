package com.vetpet.VetPet.repository;

import com.vetpet.VetPet.entity.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuardianRepository extends JpaRepository <Guardian,Long>{

    Optional<Guardian> findByPhone(String phone);

    List<Guardian> findByName(String name);

    @Query(value = "SELECT g FROM Guardian g WHERE LOWER(g.name) LIKE LOWER(CONCAT(:name, '%'))")
    List<Guardian> findLikeName(String name);
}


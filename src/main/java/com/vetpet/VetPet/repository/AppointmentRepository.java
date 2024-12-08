package com.vetpet.VetPet.repository;

import com.vetpet.VetPet.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}

package com.vetpet.VetPet.services;

import com.vetpet.VetPet.repository.AppointmentRepository;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import org.springframework.stereotype.Service;
import java.util.HashMap; import java.util.Map;

@Service
public class StatisticsService {
    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final GuardianRepository guardianRepository;

    public StatisticsService(AppointmentRepository appointmentRepository, PetRepository petRepository, GuardianRepository guardianRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.guardianRepository = guardianRepository;
    }
    public Map<String, Integer> getGlobalStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("appointments", (int) appointmentRepository.count());
        statistics.put("pets", (int) petRepository.count());
        statistics.put("guardians", (int) guardianRepository.count());
        return statistics;
    }
}

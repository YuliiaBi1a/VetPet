package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestAppointmentDto;
import com.vetpet.VetPet.dto.ResponseAppointmentDto;
import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.exceptions.NoIdFoundException;
import com.vetpet.VetPet.repository.AppointmentRepository;
import com.vetpet.VetPet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
    }

    public ResponseAppointmentDto createAppointment(RequestAppointmentDto dto) {
        Pet pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new NoIdFoundException(dto.petId()));

        Appointment appointment = new Appointment();
        appointment.setDate(dto.date());
        appointment.setTime(dto.time());
        appointment.setReason(dto.reason());
        appointment.setPet(pet);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDto(savedAppointment);
    }

    public ResponseAppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        return mapToResponseDto(appointment);
    }

    public List<ResponseAppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public ResponseAppointmentDto updateAppointment(Long id, RequestAppointmentDto dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));

        Pet pet = petRepository.findById(dto.petId())
                .orElseThrow(() -> new NoIdFoundException(dto.petId()));

        appointment.setDate(dto.date());
        appointment.setTime(dto.time());
        appointment.setReason(dto.reason());
        appointment.setPet(pet);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return mapToResponseDto(updatedAppointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        appointmentRepository.delete(appointment);
    }

    private ResponseAppointmentDto mapToResponseDto(Appointment appointment) {
        return new ResponseAppointmentDto(
                appointment.getId(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getReason(),
                appointment.getPet()
        );
    }
}

package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestAppointmentDto;
import com.vetpet.VetPet.dto.ResponseAppointmentDto;
import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.exceptions.NoIdFoundException;
import com.vetpet.VetPet.exceptions.NoRegistersFoundException;
import com.vetpet.VetPet.repository.AppointmentRepository;
import com.vetpet.VetPet.repository.PetRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PetRepository petRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
    }

    //POST
    public ResponseAppointmentDto createAppointment(RequestAppointmentDto req) {
        Pet pet = petRepository.findById(req.petId())
                .orElseThrow(() -> new NoIdFoundException(req.petId()));

        Appointment newAppointment = req.toEntity(pet);
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        return mapToResponseDto(savedAppointment);
    }

    //GET
    public ResponseAppointmentDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        return mapToResponseDto(appointment);
    }

    //GET
    public List<ResponseAppointmentDto> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new NoRegistersFoundException();
        }

        return appointments.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    //GET NEXT
    public List<ResponseAppointmentDto> getNextAppointmentsByPetId(Long petId) {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> nextAppointments = appointmentRepository.findByPetId(petId).stream()
                .filter(appointment -> appointment.getDate().atTime(appointment.getTime()).isAfter(now))
                .toList();

        return nextAppointments.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    //GET PAST
    public List<ResponseAppointmentDto> getPastAppointmentsByPetId(Long petId) {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> pastAppointments = appointmentRepository.findByPetId(petId).stream()
                .filter(appointment -> appointment.getDate().atTime(appointment.getTime()).isBefore(now))
                .toList();

        return pastAppointments.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    //PUT
    public ResponseAppointmentDto updateAppointment(Long id, RequestAppointmentDto req) {
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));

        Pet pet = petRepository.findById(req.petId())
                .orElseThrow(() -> new NoIdFoundException(req.petId()));

        existingAppointment.setDate(req.date());
        existingAppointment.setTime(req.time());
        existingAppointment.setReason(req.reason());
        existingAppointment.setPet(pet);

        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
        return mapToResponseDto(updatedAppointment);
    }

    //DELETE
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
        appointmentRepository.deleteById(id);
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

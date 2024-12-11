package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.dto.RequestAppointmentDto;
import com.vetpet.VetPet.dto.ResponseAppointmentDto;
import com.vetpet.VetPet.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<ResponseAppointmentDto> createAppointment(@RequestBody RequestAppointmentDto dto) {
        ResponseAppointmentDto createdAppointment = appointmentService.createAppointment(dto);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAppointmentDto> getAppointmentById(@PathVariable Long id) {
        ResponseAppointmentDto appointment = appointmentService.getAppointmentById(id);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ResponseAppointmentDto>> getAllAppointments() {
        List<ResponseAppointmentDto> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseAppointmentDto> updateAppointment(
            @PathVariable Long id,
            @RequestBody RequestAppointmentDto dto) {
        ResponseAppointmentDto updatedAppointment = appointmentService.updateAppointment(id, dto);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

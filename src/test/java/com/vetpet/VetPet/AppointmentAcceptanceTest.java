package com.vetpet.VetPet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetpet.VetPet.dto.RequestAppointmentDto;
import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.repository.AppointmentRepository;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private GuardianRepository guardianRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Pet testPet;

    @BeforeEach
    void setup() {
        appointmentRepository.deleteAll();
        petRepository.deleteAll();
        guardianRepository.deleteAll();

        Guardian guardian = new Guardian();
        guardian.setName("Harry Potter");
        guardian.setEmail("hp@mail.com");
        guardian.setPhone("123456789");
        guardian.setAddress("calle Diagon");

        guardianRepository.save(guardian);

        testPet = new Pet();
        testPet.setName("Buddy");
        testPet.setClass_species("dog");
        testPet.setBreed("Labrador Retriever");
        testPet.setAge(4);
        testPet.setGuardian(guardian);
        petRepository.save(testPet);
    }

    @Test
    void testCreateAppointment() throws Exception {
        RequestAppointmentDto request = new RequestAppointmentDto(
                LocalDate.of(2024, 4, 15),
                LocalTime.of(15, 30),
                "Vaccination",
                testPet.getId()
        );

        mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.date", is("2024-04-15")))
                .andExpect(jsonPath("$.time", is("15:30:00")))
                .andExpect(jsonPath("$.reason", is("Vaccination")))
                .andExpect(jsonPath("$.pet.name", is("Buddy")));
    }

    @Test
    void testGetAppointmentById() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2024, 4, 15));
        appointment.setTime(LocalTime.of(15, 30));
        appointment.setReason("Vaccination");
        appointment.setPet(testPet);
        appointmentRepository.save(appointment);

        mockMvc.perform(get("/appointments/" + appointment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(appointment.getId().intValue())))
                .andExpect(jsonPath("$.date", is("2024-04-15")))
                .andExpect(jsonPath("$.time", is("15:30:00")))
                .andExpect(jsonPath("$.reason", is("Vaccination")))
                .andExpect(jsonPath("$.pet.name", is("Buddy")));
    }

    @Test
    void testGetAllAppointments() throws Exception {
        Appointment appointment1 = new Appointment();
        appointment1.setDate(LocalDate.of(2024, 4, 15));
        appointment1.setTime(LocalTime.of(15, 30));
        appointment1.setReason("Vaccination");
        appointment1.setPet(testPet);
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setDate(LocalDate.of(2024, 4, 16));
        appointment2.setTime(LocalTime.of(10, 0));
        appointment2.setReason("Annual Checkup");
        appointment2.setPet(testPet);
        appointmentRepository.save(appointment2);

        mockMvc.perform(get("/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].reason", is("Vaccination")))
                .andExpect(jsonPath("$[1].reason", is("Annual Checkup")));
    }

    @Test
    void testUpdateAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2024, 4, 15));
        appointment.setTime(LocalTime.of(15, 30));
        appointment.setReason("Vaccination");
        appointment.setPet(testPet);
        appointmentRepository.save(appointment);

        assertNotNull(appointment.getId(), "Appointment ID should not be null");

        RequestAppointmentDto updateRequest = new RequestAppointmentDto(
                LocalDate.of(2024, 4, 16),
                LocalTime.of(14, 0),
                "Follow-up",
                testPet.getId()
        );

        assertNotNull(testPet.getId(), "Pet ID should not be null");

        mockMvc.perform(put("/api/appointments/" + appointment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(appointment.getId().intValue())))
                .andExpect(jsonPath("$.date", is("2024-04-16")))
                .andExpect(jsonPath("$.time", is("14:00:00")))
                .andExpect(jsonPath("$.reason", is("Follow-up")));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        Appointment appointment = new Appointment();
        appointment.setDate(LocalDate.of(2024, 4, 15));
        appointment.setTime(LocalTime.of(15, 30));
        appointment.setReason("Vaccination");
        appointment.setPet(testPet);
        appointmentRepository.save(appointment);

        mockMvc.perform(delete("/appointments/" + appointment.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetNextAppointmentsByPetId() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Appointment futureAppointment1 = new Appointment();
        futureAppointment1.setDate(now.plusDays(1).toLocalDate());
        futureAppointment1.setTime(now.plusHours(2).toLocalTime());
        futureAppointment1.setReason("Future Checkup");
        futureAppointment1.setPet(testPet);
        appointmentRepository.save(futureAppointment1);

        Appointment futureAppointment2 = new Appointment();
        futureAppointment2.setDate(now.plusDays(3).toLocalDate());
        futureAppointment2.setTime(now.plusHours(5).toLocalTime());
        futureAppointment2.setReason("Future Vaccination");
        futureAppointment2.setPet(testPet);
        appointmentRepository.save(futureAppointment2);

        mockMvc.perform(get("/appointments/next")
                        .param("petId", testPet.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].reason", is("Future Checkup")))
                .andExpect(jsonPath("$[1].reason", is("Future Vaccination")));
    }

    @Test
    void testGetPastAppointmentsByPetId() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Appointment pastAppointment1 = new Appointment();
        pastAppointment1.setDate(now.minusDays(2).toLocalDate());
        pastAppointment1.setTime(now.minusHours(5).toLocalTime());
        pastAppointment1.setReason("Past Vaccination");
        pastAppointment1.setPet(testPet);
        appointmentRepository.save(pastAppointment1);

        Appointment pastAppointment2 = new Appointment();
        pastAppointment2.setDate(now.minusDays(1).toLocalDate());
        pastAppointment2.setTime(now.minusHours(2).toLocalTime());
        pastAppointment2.setReason("Past Checkup");
        pastAppointment2.setPet(testPet);
        appointmentRepository.save(pastAppointment2);

        mockMvc.perform(get("/appointments/past")
                        .param("petId", testPet.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].reason", is("Past Vaccination")))
                .andExpect(jsonPath("$[1].reason", is("Past Checkup")));
    }
}

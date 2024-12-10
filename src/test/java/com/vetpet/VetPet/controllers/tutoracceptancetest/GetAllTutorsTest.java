package com.vetpet.VetPet.controllers.tutoracceptancetest;

import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.repository.GuardianRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class GetAllTutorsTest {
    @Autowired
private GuardianRepository guardianRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void given1Tutor_whenGetAllTutor_thenReturnTutor() throws Exception {
        //given
        Guardian guardian1 = new Guardian("Evelyn","Quevedo",12345678);
        guardianRepository.save(guardian1);
        Guardian guardian2 = new Guardian("Paloma","Apellido",87654321);
        guardianRepository.save(guardian2);
         //when
        mockMvc.perform(get("/api/tutors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Evelyn")))
                .andExpect(jsonPath("$[0].surname", is("Quevedo")))
                .andExpect(jsonPath("$[0].phoneNumber", is(12345678)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Paloma")))
                .andExpect(jsonPath("$[1].surname", is("Apellido")))
                .andExpect(jsonPath("$[1].phoneNumber", is(87654321)));
    }

    @Test
    void givenNonExistingTutor_whenGetTutorById_thenReturnNotFound() throws Exception {
        //when & then
        mockMvc.perform(get("/api/tutors/99") // ID que no existe.
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Código de estado 404.
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("El tutor con 99 actualmente no se encuentra asociado a un tutor registrado")); // Mensaje esperado.
    }
}


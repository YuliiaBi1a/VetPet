package com.vetpet.VetPet.controllers.tutoracceptancetest;

import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class DeleteTutorByIdTest {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void given1Tutor_whenDeleteTutorById_thenReturnOK() throws Exception {
        //given
        Tutor tutor1 = new Tutor("Evelyn","Quevedo",12345678);
        tutorRepository.save(tutor1);
        //when & then
        mockMvc.perform(delete("/api/tutors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(        " Tutor has been deleted."
                ));// Mensaje esperado.
    }

    @Test
    void givenNonExistingTutor_whenGetTutorById_thenReturnNotFound() throws Exception {
        //when & then
        mockMvc.perform(get("/api/tutors/1") // ID que no existe.
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()) // Código de estado 404.
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("El tutor con 1 actualmente no se encuentra asociado a un tutor registrado")); // Mensaje esperado.
    }
}

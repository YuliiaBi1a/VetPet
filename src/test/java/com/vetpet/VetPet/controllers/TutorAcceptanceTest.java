package com.vetpet.VetPet.controllers;

import com.vetpet.VetPet.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;

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
class TutorAcceptanceTest {
    @Autowired
private TutorRepository tutorRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void given1Tutor_whenGetAllTutor_thenReturnTutor() throws Exception {
        //given
        Tutor tutor1 = new Tutor("Evelyn","Quevedo",12345678);
        tutorRepository.save(tutor1);
         //when
        mockMvc.perform(get("/api/tutors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Evelyn")))
                .andExpect(jsonPath("$[0].surname", is("Quevedo")))
                .andExpect(jsonPath("$[0].phoneNumber", is(12345678)));

        //then

    }


}

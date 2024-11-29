package com.vetpet.VetPet.controllers.tutoracceptancetest;

import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class GetTutorByIdTest {
    @Autowired
private TutorRepository tutorRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void given1Tutor_whenGetTutorById_thenReturnTutorWithThisId() throws Exception {
        //given
        Tutor tutor1 = new Tutor("Evelyn","Quevedo",12345678);
        tutorRepository.save(tutor1);
        //when & then
        mockMvc.perform(get("/api/tutors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("Evelyn")))
                .andExpect(jsonPath("surname", is("Quevedo")))
                .andExpect(jsonPath("phoneNumber", is(12345678)));
    }
}

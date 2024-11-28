package com.vetpet.VetPet.controllers.tutoracceptancetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetpet.VetPet.Tutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest

public class PostTutorTest {
    @Autowired
    MockMvc  mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void  givenTutorData_whenSaveTutor_thenReturnNewTutor () throws Exception {

//given


            Tutor tutor = new Tutor("John","Gimenez",123456789);

        //when then

        mockMvc.perform(post("/api/tutors") // Asegúrate de usar la URL correcta para tu API
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tutor))) // Envía el JSON directamente como cadena
                .andExpect(status().isCreated()) // Espera un 201 CREATED
                .andExpect(jsonPath("$.name").value("John")) // Verifica que el nombre sea el esperado
                .andExpect(jsonPath("$.surname").value("Gimenez")) // Verifica el email
                .andExpect(jsonPath("$.phoneNumber").value(123456789));

    }


}

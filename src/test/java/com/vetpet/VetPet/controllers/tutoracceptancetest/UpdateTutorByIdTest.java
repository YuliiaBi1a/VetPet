package com.vetpet.VetPet.controllers.tutoracceptancetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.repository.GuardianRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class UpdateTutorByIdTest {
    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void given1Tutor_whenUpdateTutorById_thenReturnUpdateTutorWithThisId() throws Exception{
        //given
        Guardian guardian1 = new Guardian("Evelyn","Quevedo",987654321);
        guardianRepository.save(guardian1);

        String jsonTutor1 = """
                {
                        "name": "pruevbnba1",
                        "surname": "pruxcveba2",
                        "phoneNumber": 345
                    }
                """;
        //when & then
        mockMvc.perform(put("/api/tutors/1") // Asegúrate de usar la URL correcta para tu API
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonTutor1))
                .andExpect(status().isOk())
                .andExpect(content().string(" Tutor has been updated."));
        Guardian updatedGuardian = guardianRepository.findById(1L).get();
        assertEquals("pruevbnba1", updatedGuardian.getName());
        assertEquals("pruxcveba2", updatedGuardian.getSurname());
        assertEquals(345, updatedGuardian.getPhone());

    }



//    @Test
//    void givenNonExistingTutorPhoneNumber_whenGetTutorById_thenReturnNotFound() throws Exception {
//        //when & then
//        mockMvc.perform(get("/api/tutors/1") // ID que no existe.
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound()) // Código de estado 404.
//                .andExpect(content().contentType("text/plain;charset=UTF-8"))
//                .andExpect(content().string("El tutor con 1 actualmente no se encuentra asociado a un tutor registrado")); // Mensaje esperado.
//    }
}

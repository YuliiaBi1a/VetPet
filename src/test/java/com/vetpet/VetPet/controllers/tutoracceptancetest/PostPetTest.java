//package com.vetpet.VetPet.controllers.tutoracceptancetest;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.vetpet.VetPet.entity.Pet;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class PostPetTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void givenPetData_whensavePet_thenReturnNewPet () throws Exception{
//
//        //GIVEN
//        Pet pet = new Pet( "firulais",13,"bull dog", "canino", "antonio");
//
//        //WHEN THEN
//        mockMvc.perform(post("/api/pets")
//                .contentType(MediaType.APPLICATION_JSON)
//                .contentType(objectMapper.writeValueAsString(pet)))
//
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("firulais")) // Verifica que el nombre sea el esperado
//                .andExpect(jsonPath("$.age").value(13)) // Verifica el email
//                .andExpect(jsonPath("$.breed").value("bull dog"));
//        .andExpect(jsonPath("$.class_species").value("canino"));
//        .andExpect(jsonPath("$.tutor").value("antonio"));
//
//
//    }
//}

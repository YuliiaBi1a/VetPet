package com.vetpet.VetPet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.TutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @SpringBootTest
    @AutoConfigureMockMvc
    public class PetAcceptanceTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private PetRepository petRepository;

        @Autowired
        private TutorRepository tutorRepository;

        private Tutor testTutor;

        @BeforeEach
        void setUp() {
            petRepository.deleteAll();
            tutorRepository.deleteAll();

            testTutor = new Tutor();
            testTutor.setName("John");
            testTutor.setSurname("Doe");
            testTutor.setPhoneNumber(123456789);
            tutorRepository.save(testTutor);
        }

        @Test
        void shouldCreatePet() throws Exception {
            RequestPetDto petDto = new RequestPetDto("Buddy", 3, "Labrador", "Dog", testTutor.getId());

            mockMvc.perform(post("/api/pets")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(petDto)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name", is("Buddy")))
                    .andExpect(jsonPath("$.age", is(3)))
                    .andExpect(jsonPath("$.breed", is("Labrador")))
                    .andExpect(jsonPath("$.class_species", is("Dog")))
                    .andExpect(jsonPath("$.tutor.id", is(testTutor.getId().intValue())));
        }

        @Test
        void shouldGetAllPets() throws Exception {
            Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testTutor);
            petRepository.save(pet);

            mockMvc.perform(get("/api/pets"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is("Buddy")));
        }

        @Test
        void shouldGetPetById() throws Exception {
            Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testTutor);
            pet = petRepository.save(pet);

            mockMvc.perform(get("/api/pets/" + pet.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("Buddy")));
        }

        @Test
        void shouldUpdatePet() throws Exception {
            Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testTutor);
            pet = petRepository.save(pet);

            RequestPetDto updatedPetDto = new RequestPetDto("Max", 4, "Golden Retriever", "Dog", testTutor.getId());

            mockMvc.perform(put("/api/pets/" + pet.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updatedPetDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", is("Max")))
                    .andExpect(jsonPath("$.age", is(4)))
                    .andExpect(jsonPath("$.breed", is("Golden Retriever")));
        }

        @Test
        void shouldDeletePet() throws Exception {
            Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testTutor);
            pet = petRepository.save(pet);

            mockMvc.perform(delete("/api/pets/" + pet.getId()))
                    .andExpect(status().isNoContent());

            Optional<Pet> deletedPet = petRepository.findById(pet.getId());
            assert (deletedPet.isEmpty());
        }
    }



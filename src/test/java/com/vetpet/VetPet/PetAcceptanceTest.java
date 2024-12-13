package com.vetpet.VetPet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
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
    private GuardianRepository guardianRepository;

    private Guardian testGuardian;

    @BeforeEach
    void setUp() {
        petRepository.deleteAll();
        guardianRepository.deleteAll();

        testGuardian = new Guardian();
        testGuardian.setName("John");
        testGuardian.setEmail("Doe@gmail.com");
        testGuardian.setPhone("123456789");
        guardianRepository.save(testGuardian);


    }

    @Test
    void shouldCreatePet() throws Exception {
        RequestPetDto petDto = new RequestPetDto("Buddy", 3, "Labrador", "Dog", testGuardian.getId());

        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(petDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Buddy")))
                .andExpect(jsonPath("$.age", is(3)))
                .andExpect(jsonPath("$.breed", is("Labrador")))
                .andExpect(jsonPath("$.species", is("Dog")))
                .andExpect(jsonPath("$.guardian.id", is(testGuardian.getId().intValue())));
    }


    @Test
    void shouldGetAllPets() throws Exception {
        Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testGuardian);
        petRepository.save(pet);

        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Buddy")));
    }

    @Test
    void shouldGetPetById() throws Exception {
        Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testGuardian);
        pet = petRepository.save(pet);

        mockMvc.perform(get("/pets/" + pet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Buddy")));
    }

    @Test
    void shouldUpdatePet() throws Exception {
        Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testGuardian);
        pet = petRepository.save(pet);

        RequestPetDto updatedPetDto = new RequestPetDto("Max", 4, "Golden Retriever", "Dog", testGuardian.getId());

        mockMvc.perform(put("/pets/" + pet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPetDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Max")))
                .andExpect(jsonPath("$.age", is(4)))
                .andExpect(jsonPath("$.breed", is("Golden Retriever")));
    }

    @Test
    void shouldDeletePet() throws Exception {
        Pet pet = new Pet("Buddy", 3, "Labrador", "Dog", testGuardian);
        pet = petRepository.save(pet);

        mockMvc.perform(delete("/pets/" + pet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Pet has been deleted.")));
        Optional<Pet> deletedPet = petRepository.findById(pet.getId());
        assert (deletedPet.isEmpty());
    }

    @Test
    void shouldGetPetsByGuardianId() throws Exception {
        Pet pet1 = new Pet("Buddy", 3, "Labrador", "Dog", testGuardian);
        Pet pet2 = new Pet("Max", 4, "Golden Retriever", "Dog", testGuardian);
        petRepository.save(pet1);
        petRepository.save(pet2);

        mockMvc.perform(get("/pets?guardianId=" + testGuardian.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Buddy")))
                .andExpect(jsonPath("$[1].name", is("Max")));
    }

}



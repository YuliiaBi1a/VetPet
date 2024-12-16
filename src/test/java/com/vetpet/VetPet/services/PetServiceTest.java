package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestGuardianDto;
import com.vetpet.VetPet.dto.RequestPetDto;
import com.vetpet.VetPet.dto.ResponseGuardianDto;
import com.vetpet.VetPet.dto.ResponsePetDto;
import com.vetpet.VetPet.entity.Appointment;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.repository.AppointmentRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import com.vetpet.VetPet.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PetServiceTest {
    @Mock
    PetRepository petRepository;

    @Mock
    GuardianRepository guardianRepository;

    @Mock
    AppointmentRepository appointmentRepository;

    @InjectMocks
    private PetService petService;

    // Test unitario para crear un Pet
    @Test
    void should_createNewPet() {
        // GIVEN
        Long guardianId = 1L;
        Guardian guardian = new Guardian(guardianId, "John", "john@example.com", "123456789", "Torremolinos");
        RequestPetDto requestPet = new RequestPetDto("Rex", 5, "Labrador", "Dog", guardianId);
        Pet petToSave = requestPet.toEntity(guardian);

        Pet expectedPet = new Pet(1L, "Rex", 5, "Labrador", "Dog", guardian);
        ResponsePetDto expectedResponse = ResponsePetDto.fromEntity(expectedPet);
        Mockito.when(guardianRepository.findById(guardianId)).thenReturn(Optional.of(guardian));
        Mockito.when(petRepository.save(petToSave)).thenReturn(expectedPet);

        // WHEN
        ResponsePetDto petResponse = petService.createPet(requestPet);

        // THEN
        verify(petRepository).save(petToSave);
        assertEquals(expectedResponse, petResponse);
    }

    // Test unitario para encontrar todos los Pets
    @Test
    void should_findAllPets() {
        // GIVEN
        Pet pet1 = new Pet(1L, "Rex", 5, "Labrador", "Dog", new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos"));
        Pet pet2 = new Pet(2L, "Bella", 3, "Bulldog", "Dog", new Guardian(2L, "Jane", "jane@example.com", "987654321", "Málaga"));
        List<Pet> petList = List.of(pet1, pet2);
        List<ResponsePetDto> expectedResponse = petList.stream()
                .map(ResponsePetDto::fromEntity)
                .toList();

        Mockito.when(petRepository.findAll()).thenReturn(petList);

        // WHEN
        List<ResponsePetDto> petResponse = petService.findAllPets();

        // THEN
        assertEquals(expectedResponse, petResponse);
    }

    // Test unitario para obtener un Pet por ID
    @Test
    void should_getPetById() {
        // GIVEN
        Long petId = 1L;
        Pet pet = new Pet(petId, "Rex", 5, "Labrador", "Dog", new Guardian(petId, "John", "john@example.com", "123456789", "Torremolinos"));
        ResponsePetDto expectedResponse = ResponsePetDto.fromEntity(pet);

        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        // WHEN
        ResponsePetDto petResponse = petService.findPetById(petId);

        // THEN
        assertEquals(expectedResponse, petResponse);
    }

    // Test unitario para actualizar un Pet
    @Test
    void should_updatePet() {
        // GIVEN
        Long petId = 1L;
        RequestPetDto requestPet = new RequestPetDto("Rex Updated", 6, "Labrador", "Dog", 1L);
        Pet existingPet = new Pet(petId, "Rex", 5, "Labrador", "Dog", new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos"));

        Pet updatedPet = new Pet(petId, "Rex Updated", 6, "Labrador", "Dog", existingPet.getGuardian());
        ResponsePetDto expectedResponse = ResponsePetDto.fromEntity(updatedPet);

        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.of(existingPet));
        Mockito.when(guardianRepository.findById(requestPet.guardianId())).thenReturn(Optional.of(existingPet.getGuardian()));
        Mockito.when(petRepository.save(existingPet)).thenReturn(updatedPet);

        // WHEN
        ResponsePetDto petResponse = petService.updatePet(petId, requestPet);

        // THEN
        assertEquals(expectedResponse, petResponse);
    }

    // Test unitario para eliminar un Pet por ID
    @Test
    void should_deletePetById_when_noUpcomingAppointments() {
        // GIVEN
        Long petId = 1L;
        Pet petToDelete = new Pet(petId, "Rex", 5, "Labrador", "Dog",
                new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos"));

        // Simulamos citas pasadas o vacías
        List<Appointment> pastAppointments = List.of(
                new Appointment(LocalDate.now().minusDays(5), LocalTime.of(10, 0), "Checkup", petToDelete)
        );

        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.of(petToDelete));
        Mockito.when(appointmentRepository.findByPetId(petId)).thenReturn(pastAppointments);

        // WHEN
        petService.deletePetById(petId);

        // THEN
        verify(appointmentRepository).findByPetId(petId);
        verify(petRepository).deleteById(petId);
    }

    @Test
    void should_getPetsByGuardianId() {
        // GIVEN
        Long guardianId = 1L;
        Pet pet1 = new Pet(1L, "Rex", 5, "Labrador", "Dog", new Guardian(guardianId, "John", "john@example.com", "123456789", "Torremolinos"));
        Pet pet2 = new Pet(2L, "Bella", 3, "Bulldog", "Dog", new Guardian(guardianId, "John", "john@example.com", "123456789", "Torremolinos"));
        List<Pet> petList = List.of(pet1, pet2);
        List<ResponsePetDto> expectedResponse = petList.stream()
                .map(ResponsePetDto::fromEntity)
                .toList();

        Mockito.when(petRepository.findByGuardianId(guardianId)).thenReturn(petList);

        // WHEN
        List<ResponsePetDto> petResponse = petService.findPetsByGuardianId(guardianId);

        // THEN
        assertEquals(expectedResponse, petResponse);
    }

}
package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestGuardianDto;
import com.vetpet.VetPet.dto.ResponseGuardianDto;
import com.vetpet.VetPet.entity.Guardian;
import com.vetpet.VetPet.repository.GuardianRepository;
import com.vetpet.VetPet.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class GuardianServicesTest {
    @Mock
    GuardianRepository guardianRepository;

    @Mock
    PetRepository petRepository;

    @InjectMocks
    private GuardianServices guardianServices;

    // Test unitario para crear un Guardian
    @Test
    void should_createNewGuardian() {
        // GIVEN
        RequestGuardianDto request = new RequestGuardianDto("John", "john@example.com", "123456789", "Torremolinos");
        Guardian guardianToSave = request.toEntity();

        Guardian expectedGuardian = new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos");
        ResponseGuardianDto expectedResponse = ResponseGuardianDto.fromEntity(expectedGuardian); // Convertir a ResponseGuardianDto

        Mockito.when(guardianRepository.save(guardianToSave)).thenReturn(expectedGuardian);

        // WHEN
        ResponseGuardianDto guardianResponse = guardianServices.createGuardian(request);

        // THEN
        verify(guardianRepository).save(guardianToSave);
        assertEquals(expectedResponse, guardianResponse);
    }

    // Test unitario para encontrar todos los Guardian
    @Test
    void should_findAllGuardians() {
        // GIVEN
        Guardian guardian1 = new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos");
        Guardian guardian2 = new Guardian(2L, "Jane", "jane@example.com", "987654321", "Málaga");
        List<Guardian> guardianList = List.of(guardian1, guardian2);
        List<ResponseGuardianDto> expectedResponse = guardianList.stream()
                .map(ResponseGuardianDto::fromEntity)
                .toList();

        Mockito.when(guardianRepository.findAll()).thenReturn(guardianList);

        // WHEN
        List<ResponseGuardianDto> guardianResponse = guardianServices.findAllGuardians();

        // THEN
        assertEquals(expectedResponse, guardianResponse);
    }

    // Test unitario para obtener un Guardian por ID
    @Test
    void should_getGuardianById() {
        // GIVEN
        Guardian guardian = new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos");
        ResponseGuardianDto expectedResponse = ResponseGuardianDto.fromEntity(guardian);

        Mockito.when(guardianRepository.findById(1L)).thenReturn(Optional.of(guardian));

        // WHEN
        ResponseGuardianDto guardianResponse = guardianServices.findGuardianById(1L);

        // THEN
        assertEquals(expectedResponse, guardianResponse);
    }

    @Test
    void should_getGuardiansByName() {
        // GIVEN
        String name = "John";
        Guardian guardian1 = new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos");
        Guardian guardian2 = new Guardian(2L, "John", "john2@example.com", "987654321", "Málaga");
        List<Guardian> guardianList = List.of(guardian1, guardian2);
        List<ResponseGuardianDto> expectedResponse = guardianList.stream()
                .map(ResponseGuardianDto::fromEntity)
                .toList();

        Mockito.when(guardianRepository.findLikeName(name)).thenReturn(guardianList);

        // WHEN
        List<ResponseGuardianDto> guardianResponse = guardianServices.searchByName(name);

        // THEN
        assertEquals(expectedResponse, guardianResponse);
    }


    // Test unitario para actualizar un Guardian
    @Test
    void should_updateGuardian() {
        // GIVEN
        RequestGuardianDto request = new RequestGuardianDto("John Updated", "john.updated@example.com", "123456789", "Torremolinos");
        Guardian existingGuardian = new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos");
        Guardian updatedGuardian = new Guardian(1L, "John Updated", "john.updated@example.com", "123456789", "Torremolinos");
        ResponseGuardianDto expectedResponse = ResponseGuardianDto.fromEntity(updatedGuardian);

        Mockito.when(guardianRepository.findById(1L)).thenReturn(Optional.of(existingGuardian));
        Mockito.when(guardianRepository.save(existingGuardian)).thenReturn(updatedGuardian);

        // WHEN
        ResponseGuardianDto guardianResponse = guardianServices.updateGuardian(1L, request);

        // THEN
        assertEquals(expectedResponse, guardianResponse);
    }

    // Test unitario para eliminar un Guardian por ID
    @Test
    void should_deleteGuardianById() {
        // GIVEN
        Guardian guardianToDelete = new Guardian(1L, "John", "john@example.com", "123456789", "Torremolinos");

        Mockito.when(guardianRepository.findById(1L)).thenReturn(Optional.of(guardianToDelete));
        Mockito.when(petRepository.existsByGuardianId(1L)).thenReturn(false); // No hay pets

        // WHEN
        guardianServices.deleteGuardianById(1L);

        // THEN
        verify(guardianRepository).deleteById(1L);
    }
}

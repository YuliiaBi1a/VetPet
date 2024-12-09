package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TutorServicesTest {
    @Mock
    TutorRepository tutorRepository;

    //Test unitario
    @Test
    void should_createAValidTutor(){
        //GIVEN
        RequestTutorDto tutorRequest = new RequestTutorDto("some-name", "some-surname", 123321);
        TutorServices tutorServices = new TutorServices(tutorRepository);
        Tutor tutorToSave = new Tutor("some-name", "some-surname", 123321);

        //MOCK
        Tutor expectedTutor = new Tutor(1L, "some-name", "some-surname", 123321);
        Mockito.when(tutorRepository.save(tutorToSave)).thenReturn(expectedTutor);

        //WHEN
        Tutor tutorResponse = tutorServices.createTutor(tutorRequest);

        //THEN
        verify(tutorRepository).save(tutorToSave);
        assertEquals(expectedTutor, tutorResponse);
    }

    @Test
    void should_findAllTutors() {
        // GIVEN
        Tutor tutor1 = new Tutor(1L, "name1", "surname1", 123321);
        Tutor tutor2 = new Tutor(2L, "name2", "surname2", 654321);
        TutorServices tutorServices = new TutorServices(tutorRepository);

        Mockito.when(tutorRepository.findAll()).thenReturn(java.util.List.of(tutor1, tutor2));

        // WHEN
        List<Tutor> tutors = tutorServices.findAllTutor();

        // THEN
        assertEquals(2, tutors.size());
    }

    @Test
    void should_getTutorById() {
        // GIVEN
        Tutor expectedTutor = new Tutor(1L, "some-name", "some-surname", 123321);
        TutorServices tutorServices = new TutorServices(tutorRepository);

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.of(expectedTutor));

        // WHEN
        Tutor tutorResponse = tutorServices.findTutorById(1L);

        // THEN
        assertEquals(expectedTutor, tutorResponse);
    }

    @Test
    void should_updateTutor() {
        // GIVEN
        RequestTutorDto tutorRequest = new RequestTutorDto("new-name", "new-surname", 123321);
        TutorServices tutorServices = new TutorServices(tutorRepository);
        Tutor existingTutor = new Tutor(1L, "old-name", "old-surname", 123321);
        Tutor updatedTutor = new Tutor(1L, "new-name", "new-surname", 123321);

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.of(existingTutor));
        Mockito.when(tutorRepository.save(existingTutor)).thenReturn(updatedTutor);

        // WHEN
        Tutor tutorResponse = tutorServices.updateTutor(1L, tutorRequest);

        // THEN
        assertEquals(updatedTutor, tutorResponse);
    }

    @Test
    void should_deleteTutorById() {
        // GIVEN
        Tutor tutorToDelete = new Tutor(1L, "some-name", "some-surname", 123321);
        TutorServices tutorServices = new TutorServices(tutorRepository);

        Mockito.when(tutorRepository.findById(1L)).thenReturn(Optional.of(tutorToDelete));

        // WHEN
        tutorServices.deleteTutorById(1L);

        // THEN
        verify(tutorRepository).deleteById(1L);
    }
}

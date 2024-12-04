package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.entity.Pet;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.repository.TutorRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class TutorServices {
    private final TutorRepository TUTOR_REPOSITORY;

    public TutorServices(TutorRepository tutorRepository) {
        TUTOR_REPOSITORY = tutorRepository;
    }

    public void createTutor(RequestTutorDto tutorDto) {
        Tutor newTutor = new Tutor(tutorDto.name(),
                tutorDto.surname(), tutorDto.phoneNumber());
        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findByPhoneNumber(tutorDto.phoneNumber());
        if (optionalTutor.isPresent()) {
            throw new RuntimeException("This phone number already exists");
        }        TUTOR_REPOSITORY.save(newTutor);
    }
}
//        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findByPhoneNumber(tutor.getPhoneNumber());
//        if (optionalTutor.isPresent()){
//            return new ResponseEntity<>( "Este teléfono ya esta asociado a un tutor existente. Pertenece a " + tutor.getName() + " " + tutor.getSurname(), HttpStatus.CONFLICT);
//        }
//        Tutor newTutor = TUTOR_REPOSITORY.save(tutor);

package com.vetpet.VetPet.services;

import com.vetpet.VetPet.dto.RequestTutorDto;
import com.vetpet.VetPet.entity.Tutor;
import com.vetpet.VetPet.exceptions.DuplicatePhoneException;
import com.vetpet.VetPet.exceptions.NoIdFoundException;
import com.vetpet.VetPet.exceptions.NoRegistersFoundException;
import com.vetpet.VetPet.repository.TutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorServices {

    private final TutorRepository TUTOR_REPOSITORY;

    public TutorServices(TutorRepository tutorRepository) {
        this.TUTOR_REPOSITORY = tutorRepository;
    }
    //POST
    public Tutor createTutor(RequestTutorDto tutorDto) {
        Tutor newTutor = new Tutor(tutorDto.name(),
                tutorDto.surname(), tutorDto.phoneNumber());
        Optional<Tutor> optionalTutor = TUTOR_REPOSITORY.findByPhoneNumber(tutorDto.phoneNumber());
        if (optionalTutor.isPresent()) {
            throw new DuplicatePhoneException(tutorDto.phoneNumber());
        }
        return TUTOR_REPOSITORY.save(newTutor);
    }
    //GET
    public List<Tutor> findAllTutor() {
        List<Tutor> tutors = TUTOR_REPOSITORY.findAll();
        if (tutors.isEmpty()) {
            throw new NoRegistersFoundException();
        }
        return tutors;
    }

    public Tutor findTutorById(Long id) {
        return TUTOR_REPOSITORY.findById(id)
                .orElseThrow(() -> new NoIdFoundException(id));
    }

    public Tutor updateTutor(Long id, RequestTutorDto request) {

        Optional<Tutor> optionalEmployee = TUTOR_REPOSITORY.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new NoIdFoundException(id);
        }
        Tutor existingTutor = optionalEmployee.get();

        existingTutor.setName(request.name());
        existingTutor.setSurname(request.surname());
        existingTutor.setPhoneNumber(request.phoneNumber());

        return TUTOR_REPOSITORY.save(existingTutor);
    }
    //Delete
    public void deleteTutorById(Long id) {
        Optional<Tutor> tutorOptional = TUTOR_REPOSITORY.findById(id);
        if (tutorOptional.isEmpty()) {
            throw new NoIdFoundException(id);
        }
        TUTOR_REPOSITORY.deleteById(id);
    }
}

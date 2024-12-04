package com.vetpet.VetPet.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private int age;
    private String breed;
    private String class_species;
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    public Pet(String name, int age, String breed, String class_species, Tutor tutor ) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.class_species = class_species;
        this.tutor = tutor;
    }
}

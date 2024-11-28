package com.vetpet.VetPet;


import jakarta.persistence.*;

@Entity

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private int age;
    private String breed;
    private String class_species;

    public Pet() {
    }

    public Pet(String name, int age, String breed, String class_species) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.class_species = class_species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getClass_species() {
        return class_species;
    }

    public void setClass_species(String class_species) {
        this.class_species = class_species;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;


}

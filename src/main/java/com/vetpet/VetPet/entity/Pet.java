package com.vetpet.VetPet.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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
    private Guardian guardian;

    public Pet(String name, int age, String breed, String class_species, Guardian guardian) {
        this.name = name;
        this.age = age;
        //FIX
        if(breed.isEmpty() && breed.isBlank()){
            this.breed ="Sin especificar";
        }else {
            this.breed = breed;
        }
        this.class_species = class_species;
        this.guardian = guardian;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age && Objects.equals(id, pet.id) && Objects.equals(name, pet.name) && Objects.equals(breed, pet.breed) && Objects.equals(class_species, pet.class_species) && Objects.equals(guardian, pet.guardian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, breed, class_species, guardian);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", breed='" + breed + '\'' +
                ", class_species='" + class_species + '\'' +
                ", guardian=" + guardian +
                '}';
    }
}

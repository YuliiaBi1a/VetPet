package com.vetpet.VetPet.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Guardian(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Tutor tutor = (Tutor) o;
//        return phoneNumber == tutor.phoneNumber && Objects.equals(id, tutor.id) && Objects.equals(name, tutor.name) && Objects.equals(surname, tutor.surname);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, surname, phoneNumber);
//    }
//
//    @Override
//    public String toString() {
//        return "Tutor{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                ", phoneNumber=" + phoneNumber +
//                '}';
//    }
}

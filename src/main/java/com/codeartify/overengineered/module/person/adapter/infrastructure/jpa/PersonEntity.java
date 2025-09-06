package com.codeartify.overengineered.module.person.adapter.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class PersonEntity {
    @Id
    private String id;
    private String salutation;
    private String firstName;
    private String lastName;
    private String street;
    private String streetNumber;
    private String zip;
    private String location;
    private String country;
}

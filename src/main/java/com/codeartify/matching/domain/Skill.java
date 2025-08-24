package com.codeartify.matching.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    public Skill() {
    }

    public Skill(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill s = (Skill) o;
        return Objects.equals(name, s.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

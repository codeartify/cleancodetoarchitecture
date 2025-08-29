package com.codeartify.overengineered.module.person.adapter.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}

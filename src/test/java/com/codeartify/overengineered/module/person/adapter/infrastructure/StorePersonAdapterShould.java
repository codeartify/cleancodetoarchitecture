package com.codeartify.overengineered.module.person.adapter.infrastructure;


import com.codeartify.overengineered.contract.person.port.outbound.PersonToStore;
import com.codeartify.overengineered.contract.person.port.outbound.StorePerson;
import com.codeartify.overengineered.contract.person.port.outbound.StoredPerson;
import com.codeartify.overengineered.module.person.adapter.infrastructure.jpa.PersonEntity;
import com.codeartify.overengineered.module.person.adapter.infrastructure.jpa.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class StorePersonAdapterDatabaseTest {

    @Autowired
    private StorePerson storePersonAdapter;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void should_successfully_store_person() {
        var personToStore = new PersonToStore(
                "1f98bd2e-61b7-4201-be3c-9503ca9e92f6",
                "Mr",
                "John",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        StoredPerson stored = storePersonAdapter.storePerson(personToStore);

        assertThat(stored.salutation()).isEqualTo("Mr");
        assertThat(stored.firstName()).isEqualTo("John");
        assertThat(stored.lastName()).isEqualTo("Doe");
        assertThat(stored.street()).isEqualTo("Main Street");
        assertThat(stored.streetNumber()).isEqualTo("123");
        assertThat(stored.zip()).isEqualTo("12345");
        assertThat(stored.location()).isEqualTo("Springfield");
        assertThat(stored.country()).isEqualTo("USA");

        entityManager.flush();
        entityManager.clear();

        var all = personRepository.findAll();
        assertThat(all).hasSize(1);

        PersonEntity saved = all.getFirst();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getSalutation()).isEqualTo("Mr");
        assertThat(saved.getFirstName()).isEqualTo("John");
        assertThat(saved.getLastName()).isEqualTo("Doe");
        assertThat(saved.getStreet()).isEqualTo("Main Street");
        assertThat(saved.getStreetNumber()).isEqualTo("123");
        assertThat(saved.getZip()).isEqualTo("12345");
        assertThat(saved.getLocation()).isEqualTo("Springfield");
        assertThat(saved.getCountry()).isEqualTo("USA");
    }
}

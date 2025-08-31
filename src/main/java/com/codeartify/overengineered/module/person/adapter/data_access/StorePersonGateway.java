package com.codeartify.overengineered.module.person.adapter.data_access;


import com.codeartify.overengineered.contract.person.port.outbound.gateway.PersonToStore;
import com.codeartify.overengineered.contract.person.port.outbound.gateway.StorePerson;
import com.codeartify.overengineered.contract.person.port.outbound.gateway.StoredPerson;
import com.codeartify.overengineered.module.person.adapter.data_access.jpa.PersonEntity;
import com.codeartify.overengineered.module.person.adapter.data_access.jpa.PersonRepository;
import org.springframework.stereotype.Component;

@Component
public class StorePersonGateway implements StorePerson {

    private final PersonRepository personRepository;

    public StorePersonGateway(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public StoredPerson storePerson(PersonToStore person) {
        var entity = toPersonEntity(person);

        PersonEntity savedEntity = personRepository.save(entity);

        return toStoredPerson(savedEntity);
    }

    private static StoredPerson toStoredPerson(PersonEntity entity) {
        return new StoredPerson(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getStreet(),
                entity.getStreetNumber(),
                entity.getZip(),
                entity.getLocation(),
                entity.getCountry()
        );
    }

    private static PersonEntity toPersonEntity(PersonToStore person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.id());
        entity.setFirstName(person.firstName());
        entity.setLastName(person.lastName());
        entity.setStreet(person.street());
        entity.setStreetNumber(person.streetNumber());
        entity.setZip(person.zip());
        entity.setLocation(person.location());
        entity.setCountry(person.country());
        return entity;
    }
}

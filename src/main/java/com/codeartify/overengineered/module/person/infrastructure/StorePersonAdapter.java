package com.codeartify.overengineered.module.person.infrastructure;


import com.codeartify.overengineered.contract.person.port.outbound.StorePerson;
import com.codeartify.overengineered.module.person.domain.*;
import org.springframework.stereotype.Component;

@Component
public class StorePersonAdapter implements StorePerson {

    private final PersonRepository personRepository;

    public StorePersonAdapter(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person storePerson(Person person) {
        var entity = toPersonEntity(person);

        PersonEntity savedEntity = personRepository.save(entity);

        return mapToPerson(savedEntity);
    }

    private static Person mapToPerson(PersonEntity entity) {
        return new Person(
                new Names(
                        new Name(entity.getFirstName()),
                        new Name(entity.getLastName())
                ),
                new Address(
                        new Street(entity.getStreet()),
                        new StreetNumber(entity.getStreetNumber()),
                        new Zip(entity.getZip()),
                        new Location(entity.getLocation()),
                        new Country(entity.getCountry())
                )
        );
    }

    private static PersonEntity toPersonEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        var names = person.names();
        entity.setFirstName(names.firstName().value());
        entity.setLastName(names.lastName().value());

        var address = person.address();
        entity.setStreet(address.street().value());
        entity.setStreetNumber(address.streetNumber().value());
        entity.setZip(address.zip().value());
        entity.setLocation(address.location().value());
        entity.setCountry(address.country().value());
        return entity;
    }
}

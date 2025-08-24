package com.codeartify.overengineered.module.person.app;

import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonUseCase;
import com.codeartify.overengineered.contract.person.port.outbound.StorePerson;
import com.codeartify.overengineered.module.person.domain.*;
import org.springframework.stereotype.Service;


@Service
public class CreatePersonService implements CreatePersonUseCase {

    private final StorePerson storePerson;

    public CreatePersonService(StorePerson storePerson) {
        this.storePerson = storePerson;
    }

    @Override
    public Person createPerson(PersonCommand command) {
        Person person = new Person(
                new Names(
                        new Name(command.firstName()),
                        new Name(command.lastName())
                ),
                new Address(
                        new Street(command.street()),
                        new StreetNumber(command.streetNumber()),
                        new Zip(command.zip()),
                        new Location(command.location()),
                        new Country(command.country())
                )
        );

        return storePerson.storePerson(person);
    }
}

package com.codeartify.overengineered.module.person.app;

import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonCommand;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonResult;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonUseCase;
import com.codeartify.overengineered.contract.person.port.outbound.PersonToStore;
import com.codeartify.overengineered.contract.person.port.outbound.StorePerson;
import com.codeartify.overengineered.contract.person.port.outbound.StoredPerson;
import com.codeartify.overengineered.module.person.domain.*;
import org.springframework.stereotype.Service;


@Service
public class CreatePersonService implements CreatePersonUseCase {

    private final StorePerson storePerson;

    public CreatePersonService(StorePerson storePerson) {
        this.storePerson = storePerson;
    }

    @Override
    public CreatePersonResult createPerson(CreatePersonCommand command) {
        Person person = toPerson(command);
        var personToStore = toPersonToStore(person);
        var createdPerson = storePerson.storePerson(personToStore);
        return toResult(createdPerson);
    }

    private static CreatePersonResult toResult(StoredPerson createdPerson) {
        return new CreatePersonResult(
                createdPerson.firstName(),
                createdPerson.lastName(),
                createdPerson.street(),
                createdPerson.streetNumber(),
                createdPerson.zip(),
                createdPerson.location(),
                createdPerson.country());
    }

    private static Person toPerson(CreatePersonCommand command) {
        return new Person(
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
    }


    private static PersonToStore toPersonToStore(Person person) {
        return new PersonToStore(
                person.names().firstName().value(),
                person.names().lastName().value(),
                person.address().street().value(),
                person.address().streetNumber().value(),
                person.address().zip().value(),
                person.address().location().value(),
                person.address().country().value());
    }
}

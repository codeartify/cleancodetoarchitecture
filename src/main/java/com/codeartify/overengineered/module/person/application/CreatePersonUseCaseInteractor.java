package com.codeartify.overengineered.module.person.application;

import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonCommand;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonUseCase;
import com.codeartify.overengineered.contract.person.port.outbound.gateway.PersonToStore;
import com.codeartify.overengineered.contract.person.port.outbound.gateway.StoredPerson;
import com.codeartify.overengineered.contract.person.port.outbound.presenter.PresentCreatedPerson;
import com.codeartify.overengineered.contract.person.port.outbound.gateway.StorePerson;
import com.codeartify.overengineered.contract.person.port.outbound.presenter.PresentablePerson;
import com.codeartify.overengineered.module.person.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
public class CreatePersonUseCaseInteractor implements CreatePersonUseCase {

    private final StorePerson storePerson;

    public CreatePersonUseCaseInteractor(StorePerson storePerson) {
        this.storePerson = storePerson;
    }

    @Transactional
    @Override
    public void execute(CreatePersonCommand command, PresentCreatedPerson presentCreatedPerson) {
        Person person = toPerson(command);
        var personToStore = toPersonToStore(person);
        var storedPerson = storePerson.storePerson(personToStore);
        PresentablePerson presentablePerson = toPresentablePerson(storedPerson);
        presentCreatedPerson.present(presentablePerson);
    }

    private PresentablePerson toPresentablePerson(StoredPerson storedPerson) {
        return new PresentablePerson(
                storedPerson.id(),
                storedPerson.firstName(),
                storedPerson.lastName(),
                storedPerson.street(),
                storedPerson.streetNumber(),
                storedPerson.zip(),
                storedPerson.location(),
                storedPerson.country());
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
                UUID.randomUUID().toString(),
                person.names().firstName().value(),
                person.names().lastName().value(),
                person.address().street().value(),
                person.address().streetNumber().value(),
                person.address().zip().value(),
                person.address().location().value(),
                person.address().country().value());
    }
}

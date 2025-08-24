package com.codeartify.overengineered.contract.person.port.inbound;

public interface CreatePersonUseCase {
    CreatePersonResult createPerson(CreatePersonCommand command);
}

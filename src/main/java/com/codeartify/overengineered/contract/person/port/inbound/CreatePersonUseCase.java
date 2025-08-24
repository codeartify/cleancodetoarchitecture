package com.codeartify.overengineered.contract.person.port.inbound;

import com.codeartify.overengineered.module.person.app.PersonCommand;
import com.codeartify.overengineered.module.person.domain.Person;

public interface CreatePersonUseCase {
    Person createPerson(PersonCommand command);
}

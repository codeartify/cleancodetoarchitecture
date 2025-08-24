package com.codeartify.overengineered.contract.person.port.inbound;

import com.codeartify.overengineered.module.person.app.CreatePersonCommand;
import com.codeartify.overengineered.module.person.app.CreatePersonResult;

public interface CreatePersonUseCase {
    CreatePersonResult createPerson(CreatePersonCommand command);
}

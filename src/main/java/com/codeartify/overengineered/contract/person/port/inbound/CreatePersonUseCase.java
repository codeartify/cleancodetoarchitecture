package com.codeartify.overengineered.contract.person.port.inbound;

import com.codeartify.overengineered.contract.person.port.outbound.presenter.PresentCreatedPerson;
import org.springframework.transaction.annotation.Transactional;

public interface CreatePersonUseCase {

    @Transactional
    void execute(CreatePersonCommand command, PresentCreatedPerson presentCreatedPerson);
}

package com.codeartify.overengineered.module.person.adapter.presentation.http;

import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonResult;
import com.codeartify.overengineered.contract.person.port.outbound.presenter.PresentCreatedPerson;
import com.codeartify.overengineered.contract.person.port.outbound.presenter.PresentablePerson;
import lombok.Getter;

@Getter
public class CreatedPersonPresenter implements PresentCreatedPerson {
    private CreatePersonResult result;

    @Override
    public void present(PresentablePerson presentablePerson) {
        this.result = new CreatePersonResult(
                presentablePerson.id(),
                presentablePerson.firstName(),
                presentablePerson.lastName(),
                presentablePerson.street(),
                presentablePerson.streetNumber(),
                presentablePerson.zip(),
                presentablePerson.location(),
                presentablePerson.country());
    }

}

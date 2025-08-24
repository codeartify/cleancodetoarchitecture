package com.codeartify.overengineered.module.person.presentation;

import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonUseCase;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonCommand;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonResult;
import com.codeartify.overengineered.contract.person.api.PersonRequest;
import com.codeartify.overengineered.contract.person.api.PersonResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final CreatePersonUseCase createPersonUseCase;

    public PersonController(CreatePersonUseCase createPersonUseCase) {
        this.createPersonUseCase = createPersonUseCase;
    }

    @PostMapping
    public PersonResponse createPerson(@RequestBody PersonRequest request) {
        var personCommand = toCreatePersonCommand(request);

        var createdPerson = createPersonUseCase.createPerson(personCommand);

        return toPersonResponse(createdPerson);
    }

    private static CreatePersonCommand toCreatePersonCommand(PersonRequest request) {
        return new CreatePersonCommand(
                request.firstName(),
                request.lastName(),
                request.street(),
                request.streetNumber(),
                request.zip(),
                request.location(),
                request.country()
        );
    }

    private static PersonResponse toPersonResponse(CreatePersonResult createdPerson) {
        return new PersonResponse(
                createdPerson.firstName(),
                createdPerson.lastName(),
                createdPerson.street(),
                createdPerson.streetNumber(),
                createdPerson.zip(),
                createdPerson.location(),
                createdPerson.country());
    }
}

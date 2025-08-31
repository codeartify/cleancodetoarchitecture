package com.codeartify.overengineered.module.person.adapter.presentation.http;

import com.codeartify.overengineered.contract.person.api.PersonRequest;
import com.codeartify.overengineered.contract.person.api.PersonResponse;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonCommand;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonResult;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final CreatePersonUseCase createPersonUseCase;

    public PersonController(CreatePersonUseCase createPersonUseCase) {
        this.createPersonUseCase = createPersonUseCase;
    }

    @PostMapping
    public PersonResponse createPerson(@RequestBody PersonRequest request) {
        var personCommand = toCreatePersonCommand(request);

        var presentCreatedPerson = new CreatedPersonPresenter();

        createPersonUseCase.execute(personCommand, presentCreatedPerson);

        return toPersonResponse(presentCreatedPerson.getResult());
    }

    @GetMapping(value = "/{id}")
    public PersonResponse getPerson(@PathVariable("id") String id) {
        throw new UnsupportedOperationException("implement a simple getter that queries the database for a person with a given ID (%s).\nMake sure to run ArchitectureTest. All tests should pass.".formatted(id));
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
                createdPerson.id(),
                createdPerson.firstName(),
                createdPerson.lastName(),
                createdPerson.street(),
                createdPerson.streetNumber(),
                createdPerson.zip(),
                createdPerson.location(),
                createdPerson.country());
    }
}

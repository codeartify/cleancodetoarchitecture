package com.codeartify.overengineered.module.person.adapter.presentation.http;

import com.codeartify.overengineered.contract.person.api.PersonRequest;
import com.codeartify.overengineered.contract.person.port.inbound.CreatePersonUseCase;
import com.codeartify.overengineered.contract.person.port.outbound.presenter.PresentablePerson;
import com.codeartify.overengineered.module.person.domain.Name;
import com.codeartify.overengineered.module.person.domain.Names;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@ContextConfiguration(classes = {
        PersonController.class,
        GlobalExceptionHandler.class,
        PersonControllerIT.StubConfig.class
})
class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class StubConfig {
        @Bean
        CreatePersonUseCase createPersonUseCaseStub() {
            return (command, presentCreatedPerson) -> {
                // triggers exception if validation fails --> to check BAD_REQUEST mapping
                new Names(new Name(command.firstName()), new Name(command.lastName()));

                presentCreatedPerson.present(new PresentablePerson(
                        "1f98bd2e-61b7-4201-be3c-9503ca9e92f6",
                        command.firstName(),
                        command.lastName(),
                        command.street(),
                        command.streetNumber(),
                        command.zip(),
                        command.location(),
                        command.country()
                ));
            };
        }
    }

    @Test
    void should_create_person() throws Exception {
        var request = new PersonRequest(
                "John",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.street", is("Main Street")))
                .andExpect(jsonPath("$.streetNumber", is("123")))
                .andExpect(jsonPath("$.zip", is("12345")))
                .andExpect(jsonPath("$.location", is("Springfield")))
                .andExpect(jsonPath("$.country", is("USA")));
    }

    @Test
    void createPerson_returnsBadRequest_whenValidationFails() throws Exception {
        var invalidRequest = new PersonRequest(
                "",           // invalid first name
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name must not be null or empty"));
    }
}

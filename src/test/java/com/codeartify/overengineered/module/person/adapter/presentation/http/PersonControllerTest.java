package com.codeartify.overengineered.module.person.adapter.presentation.http;

import com.codeartify.overengineered.contract.person.api.PersonRequest;
import com.codeartify.overengineered.contract.person.api.PersonResponse;
import com.codeartify.overengineered.contract.person.exception.*;
import com.codeartify.overengineered.contract.person.port.outbound.PersonToStore;
import com.codeartify.overengineered.contract.person.port.outbound.StorePerson;
import com.codeartify.overengineered.contract.person.port.outbound.StoredPerson;
import com.codeartify.overengineered.module.person.app.CreatePersonService;
import com.codeartify.overengineered.module.person.domain.Salutation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private StorePerson storePerson;

    private PersonController controller;

    @BeforeEach
    void setUp() {
        var createPersonService = new CreatePersonService(storePerson);
        controller = new PersonController(createPersonService);
    }

    @Test
    void should_successfully_create_person() {

        var request = new PersonRequest(
                "Ms",
                "Jane",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );
        when(storePerson.storePerson(any(PersonToStore.class))).thenReturn(new StoredPerson(
                "1f98bd2e-61b7-4201-be3c-9503ca9e92f6",
                request.salutation(),
                request.firstName(),
                request.lastName(),
                request.street(),
                request.streetNumber(),
                request.zip(),
                request.location(),
                request.country()
        ));

        PersonResponse response = controller.createPerson(request);

        assertThat(response.salutation()).isEqualTo("Ms");
        assertThat(response.firstName()).isEqualTo("Jane");
        assertThat(response.lastName()).isEqualTo("Doe");
        assertThat(response.street()).isEqualTo("Main Street");
        assertThat(response.streetNumber()).isEqualTo("123");
        assertThat(response.zip()).isEqualTo("12345");
        assertThat(response.location()).isEqualTo("Springfield");
        assertThat(response.country()).isEqualTo("USA");
    }


    @Test
    void createPerson_shouldFail_whenFirstNameEmpty() {
        var request = new PersonRequest(
                "Ms", "",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(NameInvalidException.class)
                .hasMessage("Name must not be null or empty");
    }

    @Test
    void createPerson_shouldFail_whenLastNameEmpty() {
        var request = new PersonRequest(
                "Ms", "Jane",
                "",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(NameInvalidException.class)
                .hasMessage("Name must not be null or empty");

    }

    @Test
    void createPerson_shouldFail_whenStreetEmpty() {
        var request = new PersonRequest(
                "Ms", "Jane",
                "Doe",
                "",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(StreetInvalidException.class)
                .hasMessage("Street must not be null or empty");

    }

    @Test
    void createPerson_shouldFail_whenStreetNumberEmpty() {
        var request = new PersonRequest(
                "Ms", "Jane",
                "Doe",
                "Main Street",
                "",
                "12345",
                "Springfield",
                "USA"
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(StreetNumberInvalidException.class)
                .hasMessage("Street number must not be null or empty");

    }

    @Test
    void createPerson_shouldFail_whenZipInvalid() {
        var request = new PersonRequest(
                "Ms", "Jane",
                "Doe",
                "Main Street",
                "123",
                "123",
                "Springfield",
                "USA"
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(ZipInvalidException.class)
                .hasMessage("Zip must be a valid 5-digit number");

    }

    @Test
    void createPerson_shouldFail_whenLocationEmpty() {
        var request = new PersonRequest(
                "Ms", "Jane",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "",
                "USA"
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(LocationInvalidException.class)
                .hasMessage("Location must not be null or empty");

    }

    @Test
    void createPerson_shouldFail_whenCountryEmpty() {
        var request = new PersonRequest(
                "Ms", "Jane",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                ""
        );

        assertThatThrownBy(() -> controller.createPerson(request))
                .isInstanceOf(CountryInvalidException.class)
                .hasMessage("Country must not be null or empty");

    }

    @Test
    void createPerson_should_default_to_unknown_when_invalid_salutation_is_provided() {

        var request = new PersonRequest(
                "mrs",
                "Jane",
                "Doe",
                "Main Street",
                "123",
                "12345",
                "Springfield",
                "USA"
        );

        when(storePerson.storePerson(any(PersonToStore.class))).thenReturn(new StoredPerson(
                "1f98bd2e-61b7-4201-be3c-9503ca9e92f6",
                Salutation.UNKNOWN.capitalized(),
                request.firstName(),
                request.lastName(),
                request.street(),
                request.streetNumber(),
                request.zip(),
                request.location(),
                request.country()
        ));

        var person = controller.createPerson(request);

        assertThat(person.salutation()).isEqualTo("Unknown");

    }

}

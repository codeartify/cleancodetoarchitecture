package com.codeartify.overengineered.contract.person.port.outbound;

public interface StorePerson {
    StoredPerson storePerson(PersonToStore person);
}

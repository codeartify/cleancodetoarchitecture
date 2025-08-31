package com.codeartify.overengineered.contract.person.port.outbound.gateway;

public interface StorePerson {
    StoredPerson storePerson(PersonToStore person);
}

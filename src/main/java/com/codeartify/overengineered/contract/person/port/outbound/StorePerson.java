package com.codeartify.overengineered.contract.person.port.outbound;

import com.codeartify.overengineered.module.person.domain.Person;

public interface StorePerson {
    Person storePerson(Person person);
}

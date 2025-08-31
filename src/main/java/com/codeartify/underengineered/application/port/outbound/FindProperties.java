package com.codeartify.underengineered.application.port.outbound;

import com.codeartify.underengineered.domain.Property;

import java.util.List;

public interface FindProperties {
    List<Property> findAll() throws Exception;
}

package com.codeartify.underengineered.application;

import com.codeartify.underengineered.application.port.inbound.SearchProperties;
import com.codeartify.underengineered.application.port.outbound.FindProperties;
import com.codeartify.underengineered.domain.PropertySearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertySearchService implements SearchProperties {
    private final FindProperties findProperties;

    public PropertySearchService(FindProperties findProperties) {
        this.findProperties = findProperties;
    }

    @Override
    public List<Long> execute(Double x, Double y, Double radius) throws Exception {
        var search = PropertySearch.from(x, y, radius);

        var properties = this.findProperties.findAll();

        return search.findContained(properties);
    }
}

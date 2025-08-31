package com.codeartify.underengineered.application;

import com.codeartify.underengineered.domain.Search;
import com.codeartify.underengineered.adapter.data_access.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertySearchService {
    private final PropertyRepository propertyRepository;

    public PropertySearchService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Long> execute(Double x, Double y, Double r) throws Exception {
        var search = Search.from(x, y, r);

        var properties = this.propertyRepository.findAll();

        return search.findContained(properties);
    }
}

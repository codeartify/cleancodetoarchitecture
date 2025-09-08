package com.codeartify.underengineered.application;

import com.codeartify.underengineered.domain.Search;
import com.codeartify.underengineered.data_access.RealEstateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealEstateSearchService {
    private final RealEstateRepository realEstateRepository;

    public RealEstateSearchService(RealEstateRepository realEstateRepository) {
        this.realEstateRepository = realEstateRepository;
    }

    public List<Long> execute(Double x, Double y, Double r) throws Exception {
        var search = Search.from(x, y, r);

        var realEstates = this.realEstateRepository.findAll();

        return search.findContained(realEstates);
    }
}

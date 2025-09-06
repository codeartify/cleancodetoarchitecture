package com.codeartify.underengineered.application;

import com.codeartify.underengineered.application.port.inbound.SearchRealEstate;
import com.codeartify.underengineered.application.port.outbound.FindRealEstate;
import com.codeartify.underengineered.domain.RealEstateSearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealEstateSearchService implements SearchRealEstate {
    private final FindRealEstate findRealEstate;

    public RealEstateSearchService(FindRealEstate findRealEstate) {
        this.findRealEstate = findRealEstate;
    }

    @Override
    public List<Long> execute(Double x, Double y, Double radius) throws Exception {
        var search = RealEstateSearch.from(x, y, radius);

        var realEstates = this.findRealEstate.findAll();

        return search.findContained(realEstates);
    }
}

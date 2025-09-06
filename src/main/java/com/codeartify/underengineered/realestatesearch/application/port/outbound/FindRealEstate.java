package com.codeartify.underengineered.realestatesearch.application.port.outbound;

import com.codeartify.underengineered.realestatesearch.domain.RealEstate;

import java.util.List;

public interface FindRealEstate {
    List<RealEstate> findAll() throws Exception;
}

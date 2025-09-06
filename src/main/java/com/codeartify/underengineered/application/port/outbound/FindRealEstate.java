package com.codeartify.underengineered.application.port.outbound;

import com.codeartify.underengineered.domain.RealEstate;

import java.util.List;

public interface FindRealEstate {
    List<RealEstate> findAll() throws Exception;
}

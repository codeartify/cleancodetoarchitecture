package com.codeartify.underengineered.realestatesearch.application.port.inbound;

import java.util.List;

public interface SearchRealEstate {
    List<Long> execute(Double x, Double y, Double r) throws Exception;
}

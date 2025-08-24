package com.codeartify.matching.dto;

import java.util.List;

public class MatchResultDTO {
    private final Long id;
    private final String label;
    private final double score;
    private final List<String> reasons;

    public MatchResultDTO(Long id, String label, double score, List<String> reasons) {
        this.id = id;
        this.label = label;
        this.score = score;
        this.reasons = reasons;
    }

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public double getScore() {
        return score;
    }

    public List<String> getReasons() {
        return reasons;
    }
}

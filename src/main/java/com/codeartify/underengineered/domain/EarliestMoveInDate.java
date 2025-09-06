package com.codeartify.underengineered.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public record EarliestMoveInDate(Date date) {
    public EarliestMoveInDate {
        if (date == null) {
            throw new RuntimeException("Date missing");
        }
    }

    public String toAmericanFormat() {
        return new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(date);
    }
}

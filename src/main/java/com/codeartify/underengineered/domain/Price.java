package com.codeartify.underengineered.domain;

import java.text.NumberFormat;
import java.util.Locale;

public record Price(Double price) {
    public Price {
        if (price == null) {
            throw new RuntimeException("Price missing");
        }
    }

    public String toAmericanFormat() {
        return NumberFormat.getCurrencyInstance(Locale.US).format(price);
    }
}

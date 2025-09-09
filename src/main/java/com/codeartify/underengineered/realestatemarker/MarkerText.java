package com.codeartify.underengineered.realestatemarker;

import com.codeartify.underengineered.domain.Price;
import com.fasterxml.jackson.annotation.JsonValue;

public record MarkerText(@JsonValue String value) {

    public static MarkerText from(String seller, String address, Double price) {
        if (seller == null || seller.trim().isEmpty()) {
            throw new RuntimeException("Seller missing");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new RuntimeException("Address missing");
        }
        if (price == null) {
            throw new RuntimeException("Price missing");
        }

        String markerTextString = "Seller: " + seller + "\n" +
                address + "\n" +
                "Price: " + new Price(price).toAmericanFormat();

        return new MarkerText(markerTextString);
    }
}

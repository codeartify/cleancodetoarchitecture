package com.codeartify.underengineered.realestatedetails;

import com.codeartify.underengineered.domain.EarliestMoveInDate;
import com.codeartify.underengineered.domain.Phone;

import java.sql.Date;

public record RealEstateDetails(String detailsText) {

    public static RealEstateDetails from(String seller, String contactPhone, String contactEmail, Date earliestMoveInDate) {
        if (seller == null || contactPhone == null || contactEmail == null || earliestMoveInDate == null) {
            throw new RuntimeException(("Real estate details missing: " +
                    "seller, contact phone, contact email, earliest move-in date: %s, %s, %s, %s")
                    .formatted(seller, contactPhone, contactEmail, earliestMoveInDate));
        }

        var americanLocalPhone = new Phone(contactPhone).toAmericanFormat();
        var americanLocalMoveInDate = new EarliestMoveInDate(earliestMoveInDate).toAmericanFormat();

        return new RealEstateDetails("%s\nPhone: %s\nEmail: %s\nEarliest move-in date: %s"
                .formatted(seller, americanLocalPhone, contactEmail, americanLocalMoveInDate));
    }

}

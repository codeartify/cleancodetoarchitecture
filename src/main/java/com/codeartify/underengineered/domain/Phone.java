package com.codeartify.underengineered.domain;

import com.codeartify.underengineered.domain.exception.InvalidPhoneNumberException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public record Phone(String phone) {
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public Phone {
        if (phone == null || phone.trim().isEmpty()) {
            throw new RuntimeException("Phone missing");
        }
    }

    public String toAmericanFormat() {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidPhoneNumberException();
        }
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(phone, "US");
            return phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
        } catch (NumberParseException e) {
            throw new InvalidPhoneNumberException();
        }

    }
}

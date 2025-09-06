package com.codeartify.overengineered.module.person.domain;

import org.springframework.util.StringUtils;

public enum Salutation {
    MR, MS, OTHER, UNKNOWN;

    public static Salutation from(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    public String capitalized() {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}

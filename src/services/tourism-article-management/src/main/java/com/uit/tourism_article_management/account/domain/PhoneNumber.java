package com.uit.tourism_article_management.account.domain;

import com.uit.tourism_article_management.exception.ClientException;

public record PhoneNumber(String value) {
    public PhoneNumber {
        requireValidFormat(value);
    }

    static private void requireValidFormat(String value) {
        if (value == null || value.isBlank())
            throw new ClientException("Account phone number must be provided");
        if (!isValid(value))
            throw new ClientException("Account phone number must be valid");
    }

    public static boolean isValid(String raw) {
        final String regex = "^(0|\\+84)(3[2-9]|5[25689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$";
        return raw.matches(regex);
    }
}

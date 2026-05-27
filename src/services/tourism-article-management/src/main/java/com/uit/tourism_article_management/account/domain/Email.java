package com.uit.tourism_article_management.account.domain;

import com.uit.tourism_article_management.exception.ClientException;

public record Email(String value) {
    public Email {
        requireValidFormat(value);
    }

    static private void requireValidFormat(String value) {

        if (value == null || value.isBlank())
            throw new ClientException("Account email must be provided");
        if (!isValid(value))
            throw new ClientException("Account email must be valid");
    }

    public static boolean isValid(String raw) {
        final String regex = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";
        return raw.matches(regex);
    }
}

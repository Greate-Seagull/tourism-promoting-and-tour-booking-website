package com.uit.tourism_article_management.tour.domain.tour_operator;

import com.uit.tourism_article_management.exception.ClientException;

public record BankBin(String value) {
    public BankBin {
        requireBankBin(value);
    }

    private void requireBankBin(String value) {
        final String sixDigitRegex = "^\\d{6}$";
        final String eightDigitRegex = "^\\d{8}$";
        if (value == null)
            throw new ClientException("Bank bin must be provided");
        if (!value.matches(sixDigitRegex) && !value.matches(eightDigitRegex))
            throw new ClientException("Bank bin must be 6 or 8 digits");
    }
}

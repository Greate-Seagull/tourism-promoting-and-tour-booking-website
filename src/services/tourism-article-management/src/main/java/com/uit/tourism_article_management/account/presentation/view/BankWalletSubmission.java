package com.uit.tourism_article_management.account.presentation.view;

import com.uit.tourism_article_management.tour.domain.tour_operator.BankBin;

import java.util.Objects;

public record BankWalletSubmission(
        BankBin bin,
        String bankAccountId
) {
    public BankWalletSubmission {
        Objects.requireNonNull(bin, "Bank bin must be provided");
        Objects.requireNonNull(bankAccountId, "Bank account id must be provided");
    }
}

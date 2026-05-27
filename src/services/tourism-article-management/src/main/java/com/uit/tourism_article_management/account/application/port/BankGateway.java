package com.uit.tourism_article_management.account.application.port;

import com.uit.tourism_article_management.account.presentation.view.BankWalletSubmission;

import java.util.Optional;

public interface BankGateway {
    Optional<String> getName(BankWalletSubmission submit);
}

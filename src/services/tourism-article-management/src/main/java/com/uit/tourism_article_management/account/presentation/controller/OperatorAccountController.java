package com.uit.tourism_article_management.account.presentation.controller;

import com.uit.tourism_article_management.account.application.AccountCommandHandler;
import com.uit.tourism_article_management.account.presentation.view.BankWalletSubmission;
import com.uit.tourism_article_management.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operator/accounts")
public class OperatorAccountController {
    private final AccountCommandHandler accountCommandHandler;

    public OperatorAccountController(AccountCommandHandler accountCommandHandler) {
        this.accountCommandHandler = accountCommandHandler;
    }

    @PostMapping("/bank-account")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity submitBankAccount(@RequestBody BankWalletSubmission submit) {
        String unverifiedName = this.accountCommandHandler.submitBankWallet(submit, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.ok(unverifiedName);
    }

    @PostMapping("/bank-account/verify")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity verifyBankAccount() {
        this.accountCommandHandler.verifyBankWallet(SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }
}

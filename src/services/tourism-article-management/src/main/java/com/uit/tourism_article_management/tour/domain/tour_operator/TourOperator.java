package com.uit.tourism_article_management.tour.domain.tour_operator;

import com.uit.tourism_article_management.account.presentation.view.BankWalletSubmission;
import com.uit.tourism_article_management.exception.ClientException;

public class TourOperator {
    private String id;
    private BankWallet wallet;

    protected boolean hasVerifiedWallet() {
        return this.wallet.isVerified();
    }

    public String getId() {
        return this.id;
    }

    public void submit(BankWalletSubmission submit) {
        BankWallet wallet = new BankWallet();
        wallet.setBankAccount(submit.bin(), submit.bankAccountId());
        wallet.setUnverified();
        this.wallet = wallet;
    }

    public void verifyBankWallet() {
        if (wallet == null)
            throw new ClientException("You have not submit any bank wallets");
        if (wallet.isVerified())
            throw new ClientException("You have verified your bank wallet");
        this.wallet.setVerified();
    }
}

package com.uit.tourism_article_management.tour.domain.tour_operator;

import com.uit.tourism_article_management.exception.ClientException;

public class BankWallet {
    private BankBin bin;
    private String bankAccountId;
    private boolean isVerified = false;

    protected BankWallet() {}

    static protected void requireBankBin(BankBin bin) {
        if (bin == null)
            throw new ClientException("Bank bin must be provided");
    }

    protected static void requireBankAccountId(String bankAccountId) {
        if (bankAccountId == null)
            throw new  ClientException("Bank account id must be provided");
    }

    protected void setBankAccount(BankBin bin, String bankAccountId) {
        this.setBankBin(bin);
        this.setBankAccountId(bankAccountId);
    }

    private void setBankAccountId(String bankAccountId) {
        requireBankAccountId(bankAccountId);
        this.bankAccountId = bankAccountId;
    }

    private void setBankBin(BankBin bin) {
        requireBankBin(bin);
        this.bin = bin;
    }

    protected void setUnverified() {
        this.isVerified = false;
    }

    public boolean isVerified() {
        return this.isVerified;
    }

    protected void setVerified() {
        this.isVerified = true;
    }
}

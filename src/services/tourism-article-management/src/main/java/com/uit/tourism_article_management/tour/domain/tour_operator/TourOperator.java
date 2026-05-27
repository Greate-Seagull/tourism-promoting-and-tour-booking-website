package com.uit.tourism_article_management.tour.domain.tour_operator;

public class TourOperator {
    private String id;
    private BankWallet wallet;

    protected boolean hasVerifiedWallet() {
        return this.wallet.isVerified();
    }

    public String getId() {
        return this.id;
    }
}

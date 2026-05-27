package com.uit.tourism_article_management.order.domain;

public class Transaction {
    private String id;
    private long amount;
    private String receiverId;
    private TransactionStatus status;

    public String getId() {
        return this.id;
    }

    public boolean isSucceededWith(long price, String receivedBankAccountId) {
        return this.isSuccessful() && this.isAmountCorrect(price) && this.isReceiverCorrect(receivedBankAccountId);
    }

    private boolean isReceiverCorrect(String receivedBankAccountId) {
        return this.receiverId.equals(receivedBankAccountId);
    }

    private boolean isAmountCorrect(long price) {
        return this.amount == price;
    }

    private boolean isSuccessful() {
        return this.status == TransactionStatus.TRANSFERED;
    }

    public boolean isRefunded() {
        return this.status == TransactionStatus.REFUNDED;
    }
}

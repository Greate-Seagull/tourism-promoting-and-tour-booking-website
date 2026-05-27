package com.uit.tourism_article_management.order.application.port;

import com.uit.tourism_article_management.order.domain.Order;
import com.uit.tourism_article_management.order.domain.Transaction;
import com.uit.tourism_article_management.tour.domain.tour_operator.BankWallet;

import java.util.Optional;

public interface PaymentRepository {
    String getQr(Order order, BankWallet wallet, String signature);

    Optional<Transaction> getById(String transactionId);

    void requestRefund(Order order, String signature);
}

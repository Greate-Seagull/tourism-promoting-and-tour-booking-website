package com.uit.tourism_article_management.order.presentation.view;

import com.uit.tourism_article_management.account.domain.Email;
import com.uit.tourism_article_management.account.domain.PhoneNumber;
import com.uit.tourism_article_management.order.domain.OrderStatus;

import java.time.LocalDate;

public record CompleteOrder(
        String id,
        String tourId,
        LocalDate takeOffDate,
        String accountId,
        String fullname,
        Email email,
        PhoneNumber phoneNumber,
        long price,
        String transactionId,
        String receivedBankAccountId,
        OrderStatus status
) {
}

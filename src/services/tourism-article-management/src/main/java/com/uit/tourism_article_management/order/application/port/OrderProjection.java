package com.uit.tourism_article_management.order.application.port;

import com.uit.tourism_article_management.order.presentation.view.CompleteOrder;

import java.util.Optional;

public interface OrderProjection {
    Optional<CompleteOrder> getById(String orderId, String accountId);
}

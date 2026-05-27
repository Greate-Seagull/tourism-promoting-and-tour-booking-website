package com.uit.tourism_article_management.order.presentation.view;

import com.uit.tourism_article_management.order.domain.OrderStatus;

import java.time.LocalDate;

public record OrderQuery(
        String id,

        LocalDate takeOffFrom,
        String tourId,

        Integer minPrice,
        Integer maxPrice,

        OrderStatus status,
        boolean refundable
) {
}

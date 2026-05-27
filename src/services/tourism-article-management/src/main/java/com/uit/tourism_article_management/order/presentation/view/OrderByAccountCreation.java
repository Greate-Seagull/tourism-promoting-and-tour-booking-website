package com.uit.tourism_article_management.order.presentation.view;

import com.uit.tourism_article_management.order.domain.Tourist;

import java.time.LocalDate;
import java.util.List;

public record OrderByAccountCreation(
        String tourId,
        LocalDate takeOffDate,
        List<Tourist> tourists
) {
}

package com.uit.tourism_article_management.order.presentation.view;

import com.uit.tourism_article_management.order.domain.Representative;
import com.uit.tourism_article_management.order.domain.Tourist;

import java.time.LocalDate;
import java.util.List;

public record OrderByContactCreation(
        String tourId,
        LocalDate takeOffDate,
        Representative representative,
        List<Tourist> tourists
) {
}

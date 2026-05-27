package com.uit.tourism_article_management.tour.domain.price_table;

public record AgeGroupPrice(
        String name,
        int from,
        int to,
        int price
) {
}

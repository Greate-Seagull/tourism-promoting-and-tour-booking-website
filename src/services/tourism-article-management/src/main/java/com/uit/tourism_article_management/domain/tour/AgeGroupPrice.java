package com.uit.tourism_article_management.domain.tour;

public record AgeGroupPrice(
        String name,
        int from,
        int to,
        int price
) {
}

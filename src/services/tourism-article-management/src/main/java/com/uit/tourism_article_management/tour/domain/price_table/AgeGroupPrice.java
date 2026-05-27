package com.uit.tourism_article_management.tour.domain.price_table;

public record AgeGroupPrice(
        String name,
        int from,
        int to,
        int price
) {
    public boolean isSatisfiedBy(int age) {
        return from <= age && age <= to;
    }

    public long getPrice() {
        return price;
    }
}

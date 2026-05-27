package com.uit.tourism_article_management.tour.domain.order;

import java.time.LocalDate;

public class Order {
    private LocalDate takeOffDate;
    private String accountId;

    public LocalDate getTakeOffDate() {
        return this.takeOffDate;
    }

    public boolean isOwnedBy(String accountId) {
        return this.accountId.equals(accountId);
    }
}

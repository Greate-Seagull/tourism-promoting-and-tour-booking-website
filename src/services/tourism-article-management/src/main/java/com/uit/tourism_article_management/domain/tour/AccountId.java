package com.uit.tourism_article_management.domain.tour;

public record AccountId(String value) {
    public static AccountId of(String id) {
        return new AccountId(id);
    }
}

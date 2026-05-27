package com.uit.tourism_article_management.tour.domain;

public record Description(
        String attractions,
        String eats,
        String whoIsItFor,
        String idealTime,
        String offer
) {
    public static Description empty() {

    }
}

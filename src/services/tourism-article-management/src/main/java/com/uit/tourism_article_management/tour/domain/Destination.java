package com.uit.tourism_article_management.tour.domain;

public record Destination(
        String name,
        String country
) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Destination other) {
            return this.name.equals(other.name) && this.country.equals(other.country);
        }
        return false;
    }
}

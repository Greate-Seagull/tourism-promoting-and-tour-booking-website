package com.uit.tourism_article_management.tour.domain;

public record Destination(
        String name,
        String country
) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Destination(String name1, String country1)) {
            return this.name.equals(name1) && this.country.equals(country1);
        }
        return false;
    }
}

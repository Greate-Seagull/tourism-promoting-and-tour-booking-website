package com.uit.tourism_article_management.domain.tour;

public record Destination(String id, String name, String countryCode) {
    public boolean isOversea(Destination dropOff) {
        return !this.countryCode.equalsIgnoreCase(dropOff.countryCode());
    }
}

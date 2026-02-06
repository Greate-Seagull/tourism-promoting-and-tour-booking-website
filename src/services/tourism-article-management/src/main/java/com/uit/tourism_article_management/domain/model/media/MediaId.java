package com.uit.tourism_article_management.domain.model.media;

public record MediaId(String id) {
    @Override
    public String toString() {
        return id;
    }
}

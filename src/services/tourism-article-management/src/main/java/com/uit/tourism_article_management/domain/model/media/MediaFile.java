package com.uit.tourism_article_management.domain.model.media;

public record MediaFile(String name, MediaType type, String resourceId) {
    public MediaFile withResource(MediaType type, String resourceId) {
        return new MediaFile(this.name, type, resourceId);
    }
}

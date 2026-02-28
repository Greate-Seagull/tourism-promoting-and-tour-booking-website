package com.uit.tourism_article_management.domain.model.article;

import java.util.UUID;

public record ArticleId(String id) {
    public static ArticleId existingArticleId(String rawId) {
        return new ArticleId(rawId);
    }

    public static ArticleId nextIdentity() {
        return new ArticleId(UUID.randomUUID().toString());
    }

    @Override
    public String toString() {
        return id;
    }
}

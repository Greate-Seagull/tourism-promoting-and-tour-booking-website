package com.uit.tourism_article_management.domain.model.article;

import java.util.Objects;
import java.util.UUID;

public record ArticleBlockId(String id) {
    public static ArticleBlockId existingBlockId(String id) {
        return new ArticleBlockId(id);
    }

    public static ArticleBlockId nextIdentity() {
        return new ArticleBlockId(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArticleBlockId anotherId){
            return this.id.equals(anotherId.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}

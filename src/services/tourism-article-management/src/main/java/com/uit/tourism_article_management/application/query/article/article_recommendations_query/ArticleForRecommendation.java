package com.uit.tourism_article_management.application.query.article.article_recommendations_query;

import com.uit.tourism_article_management.domain.model.article.ArticleId;

import java.util.Collection;

public record ArticleForRecommendation(ArticleId id, Collection<String> content) {
    public Collection<String> getContent() { return this.content;}

    public ArticleId getId() {
        return this.id;
    }
}

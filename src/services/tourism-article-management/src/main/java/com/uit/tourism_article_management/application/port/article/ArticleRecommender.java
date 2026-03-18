package com.uit.tourism_article_management.application.port.article;

import com.uit.tourism_article_management.application.model.ArticleView;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleForRecommendation;
import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.article.ArticleId;

import java.util.Collection;

public interface ArticleRecommender {
    void ingest(ArticleId id, Collection<ArticleBlock> oldContent, Collection<ArticleBlock> newContent);
    Collection<ArticleId> recommend(ArticleForRecommendation article, int limit);
}
package com.uit.tourism_article_management.application.port.article;

import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleForRecommendation;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleTeaser;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.article.BlockType;

import java.util.Collection;

public interface ArticleProjector {
    ArticleForRecommendation getArticleForRecommendation(ArticleId id, Collection<BlockType> textTypes);
    Collection<ArticleTeaser> getArticleTeaserById(Collection<ArticleId> ids);
}

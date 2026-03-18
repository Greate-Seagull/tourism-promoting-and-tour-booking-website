package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.port.article.ArticleProjector;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleForRecommendation;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleTeaser;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.article.BlockType;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SpringDataArticleProjector implements ArticleProjector {
    private final JpaArticleProjector projector;

    public SpringDataArticleProjector(JpaArticleProjector projector) {
        this.projector = projector;
    }

    @Override
    public ArticleForRecommendation getArticleForRecommendation(ArticleId id, Collection<BlockType> textTypes) {
        var content = this.projector.findArticleForRecommendation(id.id(), textTypes.stream().map(BlockType::name).toList());
        return new ArticleForRecommendation(id, content);
    }

    @Override
    public Collection<ArticleTeaser> getArticleTeaserById(Collection<ArticleId> ids) {
        return this.projector.findSummariesById(ids.stream().map(ArticleId::id).toList());
    }
}

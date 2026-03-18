package com.uit.tourism_article_management.application.query.article.article_recommendations_query;

import com.uit.tourism_article_management.application.model.ArticleView;
import com.uit.tourism_article_management.application.port.article.ArticleProjector;
import com.uit.tourism_article_management.application.port.article.ArticleRecommender;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.article.BlockType;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class RecommendArticlesQuery {
    private final ArticleRecommender recommender;
    private final ArticleProjector read;

    public RecommendArticlesQuery(
            ArticleRecommender recommender,
            ArticleProjector read
    ) {
        this.recommender = recommender;
        this.read = read;
    }

    public Collection<ArticleTeaser> execute(String id) {
        var article = this.read.getArticleForRecommendation(ArticleId.existing(id), BlockType.TEXT_TYPES);
        var ids = this.recommender.recommend(article, 4);
        System.out.println(ids);
        return this.read.getArticleTeaserById(ids);
    }
}
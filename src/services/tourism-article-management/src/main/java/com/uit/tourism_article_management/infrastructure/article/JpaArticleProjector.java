package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.model.ArticleBlockView;
import com.uit.tourism_article_management.application.model.ArticleView;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleTeaser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface JpaArticleProjector extends JpaRepository<JpaArticle, String> {
    @Query("SELECT b.content " +
            "FROM JpaArticleBlock b " +
            "WHERE b.id.articleId = :id AND b.type IN :textTypes " +
            "ORDER BY b.blockOrder ASC"
    )
    Collection<String> findArticleForRecommendation(@Param("id") String id, @Param("textTypes") List<String> textTypes);

    @Query("SELECT a.id, a.title, a.coverImageId " +
            "FROM JpaArticle a " +
            "WHERE a.id IN :ids")
    Collection<ArticleTeaser> findSummariesById(@Param("ids") Collection<String> ids);
}

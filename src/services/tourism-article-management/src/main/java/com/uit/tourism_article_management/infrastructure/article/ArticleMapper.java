package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.domain.model.article.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public JpaArticle toPersistence(Article article) {
        if (article == null)
            return null;

        JpaArticle jpaArticle = new JpaArticle();
        // This is for JPA to find the record
        jpaArticle.setArticleId(article.getId().id());

        jpaArticle.setTitle(article.getTitle());
        jpaArticle.setIntroduction(article.getIntroduction());
        jpaArticle.setCoverImageId(article.getCoverImageId());

        return jpaArticle;
    }

    public Article toDomain(JpaArticle jpaArticle) {
        if (jpaArticle == null)
            return null;

        return Article.rehydrate(
                jpaArticle.getArticleId(),
                jpaArticle.getTitle(),
                jpaArticle.getIntroduction(),
                jpaArticle.getCoverImageId()
        );
    }
}

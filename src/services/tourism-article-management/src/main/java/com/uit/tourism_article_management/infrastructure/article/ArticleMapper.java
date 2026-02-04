package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.domain.model.article.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public JpaArticle toPersistence(Article article) {
        if (article == null)
            return null;

        return new JpaArticle(
                article.getTitle(),
                article.getIntroduction(),
                article.getCoverImageId()
        );
    }
}

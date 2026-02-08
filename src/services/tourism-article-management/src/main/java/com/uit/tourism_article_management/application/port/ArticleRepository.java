package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

public interface ArticleRepository {
    void save(Article article);
    Optional<Article> getById(ArticleId articleId);
    ArticleId nextArticleIdentity();
    MediaId nextMediaIdentity();
}

package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.model.article.Article;

public interface ArticleRepository {
    void save(Article article);
}

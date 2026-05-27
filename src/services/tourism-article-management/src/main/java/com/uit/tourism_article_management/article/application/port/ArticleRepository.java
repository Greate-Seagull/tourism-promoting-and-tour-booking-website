package com.uit.tourism_article_management.article.application.port;

import com.uit.tourism_article_management.article.domain.Report;
import com.uit.tourism_article_management.article.domain.Article;

import java.util.Optional;

public interface ArticleRepository {
    void save(Article article);

    Optional<Article> getById(String articleId);

    void save(Report report);

    Optional<Article> getById(String articleId, String accountId);

    void deleteByIdAndAccountId(String articleId, String accountId);
}

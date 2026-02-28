package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SpringDataArticleRepository implements ArticleRepository {
    private final JpaArticleRepository repository;

    public SpringDataArticleRepository(JpaArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Article article) {
        this.repository.save(JpaArticle.fromDomain(article));
    }

    @Override
    public Optional<Article> getById(ArticleId articleId) {
        return this.repository.findById(articleId.id()).map(JpaArticle::toDomain);
    }
}

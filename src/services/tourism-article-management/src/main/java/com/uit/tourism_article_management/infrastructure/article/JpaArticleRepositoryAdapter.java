package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.port.ArticleRepository;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaArticleRepositoryAdapter implements ArticleRepository {
    private final JpaArticleRepository repository;
    private final ArticleMapper mapper;

    public JpaArticleRepositoryAdapter(JpaArticleRepository repository, ArticleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void save(Article article){
        this.repository.save(mapper.toPersistence(article));
    }

    @Override
    public Optional<Article> getById(ArticleId articleId) {
        return repository.findById(articleId.toString()).map(mapper::toDomain);
    }

    @Override
    public ArticleId nextArticleIdentity() {
        return new ArticleId(UUID.randomUUID().toString());
    }

    @Override
    public MediaId nextMediaIdentity() {
        return new MediaId(UUID.randomUUID().toString());
    }
}

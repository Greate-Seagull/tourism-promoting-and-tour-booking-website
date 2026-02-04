package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.port.ArticleRepository;
import com.uit.tourism_article_management.domain.model.article.Article;
import org.springframework.stereotype.Repository;

@Repository
public class JpaArticleRepositoryAdapter implements ArticleRepository {
    private final JpaArticleRepository repository;
    private final ArticleMapper mapper;

    public JpaArticleRepositoryAdapter(JpaArticleRepository repository, ArticleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(Article article){
        this.repository.save(mapper.toPersistence(article));
    }
}

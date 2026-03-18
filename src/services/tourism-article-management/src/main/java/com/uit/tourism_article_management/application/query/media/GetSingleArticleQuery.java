package com.uit.tourism_article_management.application.query.media;

import com.uit.tourism_article_management.application.exception.ApplicationException;
import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.model.DomainException;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import org.springframework.stereotype.Service;

@Service
public class GetSingleArticleQuery {
    private final ArticleRepository repo;

    public GetSingleArticleQuery(ArticleRepository repo) {
        this.repo = repo;
    }

    public Article execute(String id) {
        return this.repo.getById(ArticleId.existing(id))
                .orElseThrow(() -> ApplicationException.notfound(id));
    }
}

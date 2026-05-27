package com.uit.tourism_article_management.article.presentation.controller;

import com.uit.tourism_article_management.article.application.port.ArticleProjection;
import com.uit.tourism_article_management.article.infrastructure.persistence.JpaArticleRepository;
import com.uit.tourism_article_management.article.presentation.view.ArticleQuery;
import com.uit.tourism_article_management.article.presentation.view.CompleteArticle;
import com.uit.tourism_article_management.utils.QueryDslPredicateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/articles")
public class PublicArticleController {
    private final JpaArticleRepository repository;
    private final ArticleProjection projection;

    public PublicArticleController(JpaArticleRepository repository, ArticleProjection projection) {
        this.repository = repository;
        this.projection = projection;
    }

    @GetMapping
    public ResponseEntity search(
            @ModelAttribute ArticleQuery query,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                this.repository.findAll(QueryDslPredicateBuilder.from(query), pageable)
        );
    }

    @GetMapping("/{articleId}")
    public ResponseEntity get(@PathVariable String articleId) {
        CompleteArticle article = this.projection.findCompleteById(articleId);
        return ResponseEntity.ok(article);
    }
}

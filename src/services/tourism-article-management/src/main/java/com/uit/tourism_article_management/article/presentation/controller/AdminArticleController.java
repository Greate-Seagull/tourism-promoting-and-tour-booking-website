package com.uit.tourism_article_management.article.presentation.controller;

import com.uit.tourism_article_management.article.application.port.ArticleProjection;
import com.uit.tourism_article_management.article.infrastructure.persistence.JpaArticleRepository;
import com.uit.tourism_article_management.article.presentation.view.AdminArticleQuery;
import com.uit.tourism_article_management.article.presentation.view.CompleteReport;
import com.uit.tourism_article_management.utils.QueryDslPredicateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/articles")
public class AdminArticleController {
    private final ArticleProjection projection;
    private final JpaArticleRepository repository;

    public AdminArticleController(ArticleProjection projection, JpaArticleRepository repository) {
        this.projection = projection;
        this.repository = repository;
    }

    @GetMapping("/{articleId}/reports")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getReports(@PathVariable String articleId){
        List<CompleteReport> reports = projection.findReportsOfArticle(articleId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity searchForArticles(
            @ModelAttribute AdminArticleQuery query,
            Pageable pageable
    ){
        return ResponseEntity.ok(
                this.repository.findAll(QueryDslPredicateBuilder.from(query), pageable)
        );
    }
}

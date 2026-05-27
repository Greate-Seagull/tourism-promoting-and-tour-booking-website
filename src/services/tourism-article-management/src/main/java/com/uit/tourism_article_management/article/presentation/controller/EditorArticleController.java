package com.uit.tourism_article_management.article.presentation.controller;

import com.uit.tourism_article_management.article.application.port.ArticleProjection;
import com.uit.tourism_article_management.article.infrastructure.utils.MapstructArticleMapper;
import com.uit.tourism_article_management.article.application.ArticleCommandHandler;
import com.uit.tourism_article_management.article.domain.Article;
import com.uit.tourism_article_management.article.presentation.view.*;
import com.uit.tourism_article_management.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/editor/articles")
public class EditorArticleController {
    private final ArticleCommandHandler commandHandler;
    private final MapstructArticleMapper mapper;
    private final ArticleProjection projection;

    public EditorArticleController(ArticleCommandHandler commandHandler, MapstructArticleMapper mapper, ArticleProjection projection) {
        this.commandHandler = commandHandler;
        this.mapper = mapper;
        this.projection = projection;
    }

    @PostMapping
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity create(@RequestBody ArticleCreation creation) {
        Article article = this.commandHandler.create(creation, SecurityUtils.getRequiredAccountId());
        CompleteArticle complete = this.mapper.toComplete(article);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{articleId}")
                .buildAndExpand(complete.id())
                .toUri();

        return ResponseEntity.created(location).body(complete);
    }

    @PatchMapping("/{articleId}")
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity edit(
            @PathVariable String articleId,
            @RequestBody ArticleModification modification
    ) {
        Article article = this.commandHandler.edit(articleId, modification, SecurityUtils.getRequiredAccountId());
        ArticleModifiable modifiable = this.mapper.toModifiable(article);
        return ResponseEntity.ok(modifiable);
    }

    @PostMapping("/{articleId}/enrich")
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity edit(
            @PathVariable String articleId,
            @RequestBody ArticleEnrichment enrichment
    ) {
        this.commandHandler.enrich(articleId, enrichment, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{article}")
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity remove(@PathVariable String articleId) {
        this.commandHandler.remove(articleId, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    @PreAuthorize("hasRole('EDITOR')")
    public ResponseEntity getAll() {
        List<SummarizedArticle> articles = this.projection.findSummariesOfEditor(SecurityUtils.getRequiredAccountId());
        return ResponseEntity.ok(articles);
    }
}

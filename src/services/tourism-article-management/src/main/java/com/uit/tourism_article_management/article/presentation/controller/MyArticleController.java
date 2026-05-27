package com.uit.tourism_article_management.article.presentation.controller;

import com.uit.tourism_article_management.article.application.ArticleCommandHandler;
import com.uit.tourism_article_management.article.presentation.view.ReportCreation;
import com.uit.tourism_article_management.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me/articles")
public class MyArticleController {
    private final ArticleCommandHandler commandHandler;

    public MyArticleController(ArticleCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping("/{articleId}/reports")
    public ResponseEntity report(
            @PathVariable String articleId,
            @RequestBody ReportCreation creation
    ) {
        this.commandHandler.report(articleId, creation, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }
}

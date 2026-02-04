package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.create_article.CreateArticleCommand;
import com.uit.tourism_article_management.application.command.create_article.CreateArticleUsecase;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final CreateArticleUsecase createArticleUsecase;

    public ArticleController(CreateArticleUsecase createArticleUsecase) {
        this.createArticleUsecase = createArticleUsecase;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createArticle(@RequestBody CreateArticleCommand request){
        this.createArticleUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }
}

package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.change_introduction.ChangeIntroductionCommand;
import com.uit.tourism_article_management.application.command.change_introduction.ChangeIntroductionUsecase;
import com.uit.tourism_article_management.application.command.change_title.ChangeTitleCommand;
import com.uit.tourism_article_management.application.command.change_title.ChangeTitleUsecase;
import com.uit.tourism_article_management.application.command.create_article.CreateArticleCommand;
import com.uit.tourism_article_management.application.command.create_article.CreateArticleResult;
import com.uit.tourism_article_management.application.command.create_article.CreateArticleUsecase;
import com.uit.tourism_article_management.application.command.initiate_article_creating_session.InitiateArticleCreatingSession;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import com.uit.tourism_article_management.presentation.dto.ChangeIntroductionRequestBody;
import com.uit.tourism_article_management.presentation.dto.ChangeTitleRequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final CreateArticleUsecase createArticleUsecase;
    private final ChangeTitleUsecase changeTitleUsecase;
    private final ChangeIntroductionUsecase changeIntroductionUsecase;
    private final InitiateArticleCreatingSession initiateArticleCreatingSession;

    public ArticleController(CreateArticleUsecase createArticleUsecase,
                             ChangeTitleUsecase changeTitleUsecase,
                             ChangeIntroductionUsecase changeIntroductionUsecase, InitiateArticleCreatingSession initiateArticleCreatingSession
    ) {
        this.createArticleUsecase = createArticleUsecase;
        this.changeTitleUsecase = changeTitleUsecase;
        this.changeIntroductionUsecase = changeIntroductionUsecase;
        this.initiateArticleCreatingSession = initiateArticleCreatingSession;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createArticle(@RequestBody CreateArticleCommand request){
        final CreateArticleResult result = this.createArticleUsecase.execute(request);
        final String token = this.initiateArticleCreatingSession.execute(result.title(), result.coverImageId());

        URI location = UriComponentsBuilder
                .fromUriString("http://localhost:8080/media")
                .queryParam("session_id", token)
                .build()
                .toUri();

        return ResponseEntity.created(location).body(ApiResponse.builder().build());
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<ApiResponse> changeTitle(@PathVariable String id, @RequestBody ChangeTitleRequestBody body) {
        ChangeTitleCommand request = new ChangeTitleCommand(id, body.title());
        this.changeTitleUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PatchMapping("/{id}/introduction")
    public ResponseEntity<ApiResponse> changeIntroduction(@PathVariable String id, @RequestBody ChangeIntroductionRequestBody body) {
        ChangeIntroductionCommand request = new ChangeIntroductionCommand(id, body.introduction());
        this.changeIntroductionUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }
}

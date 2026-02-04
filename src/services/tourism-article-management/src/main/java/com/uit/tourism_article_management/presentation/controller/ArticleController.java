package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.change_introduction.ChangeIntroductionCommand;
import com.uit.tourism_article_management.application.command.change_introduction.ChangeIntroductionUsecase;
import com.uit.tourism_article_management.application.command.change_title.ChangeTitleCommand;
import com.uit.tourism_article_management.application.command.change_title.ChangeTitleUsecase;
import com.uit.tourism_article_management.application.command.create_article.CreateArticleCommand;
import com.uit.tourism_article_management.application.command.create_article.CreateArticleUsecase;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import com.uit.tourism_article_management.presentation.dto.ChangeIntroductionRequestBody;
import com.uit.tourism_article_management.presentation.dto.ChangeTitleRequestBody;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final CreateArticleUsecase createArticleUsecase;
    private final ChangeTitleUsecase changeTitleUsecase;
    private final ChangeIntroductionUsecase changeIntroductionUsecase;

    public ArticleController(CreateArticleUsecase createArticleUsecase, ChangeTitleUsecase changeTitleUsecase, ChangeIntroductionUsecase changeIntroductionUsecase) {
        this.createArticleUsecase = createArticleUsecase;
        this.changeTitleUsecase = changeTitleUsecase;
        this.changeIntroductionUsecase = changeIntroductionUsecase;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createArticle(@RequestBody CreateArticleCommand request){
        this.createArticleUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PatchMapping("/{id}/title")
    public ResponseEntity<ApiResponse> changeTitle(@PathVariable String id, @RequestBody ChangeTitleRequestBody body) {
        ChangeTitleCommand request = new ChangeTitleCommand(id, body.title());
        this.changeTitleUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PatchMapping("{id}/introduction")
    public ResponseEntity<ApiResponse> changeIntroduction(@PathVariable String id, @RequestBody ChangeIntroductionRequestBody body) {
        ChangeIntroductionCommand request = new ChangeIntroductionCommand(id, body.introduction());
        this.changeIntroductionUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }
}

package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.sync.article.change_introduction.ChangeIntroductionCommand;
import com.uit.tourism_article_management.application.command.sync.article.change_introduction.ChangeIntroductionUsecase;
import com.uit.tourism_article_management.application.command.sync.article.change_title.ChangeTitleCommand;
import com.uit.tourism_article_management.application.command.sync.article.change_title.ChangeTitleUsecase;
import com.uit.tourism_article_management.application.command.sync.article.create_article.CreateArticleCommand;
import com.uit.tourism_article_management.application.command.sync.article.create_article.CreateArticleResult;
import com.uit.tourism_article_management.application.command.sync.article.create_article.CreateArticleUsecase;
import com.uit.tourism_article_management.application.command.sync.article.edit_article_content.EditArticleContentCommand;
import com.uit.tourism_article_management.application.command.sync.article.edit_article_content.EditArticleContentUsecase;
import com.uit.tourism_article_management.application.command.sync.token.initiate_article_creating_session.InitiateArticleCreatingSession;
import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.article.EditBlockCommand;
import com.uit.tourism_article_management.presentation.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final CreateArticleUsecase createArticleUsecase;
    private final ChangeTitleUsecase changeTitleUsecase;
    private final ChangeIntroductionUsecase changeIntroductionUsecase;
    private final InitiateArticleCreatingSession initiateArticleCreatingSession;
    private final EditArticleContentUsecase editArticleContentUsecase;

    private final ArticleRepository repo;

    public ArticleController(CreateArticleUsecase createArticleUsecase,
                             ChangeTitleUsecase changeTitleUsecase,
                             ChangeIntroductionUsecase changeIntroductionUsecase,
                             InitiateArticleCreatingSession initiateArticleCreatingSession,
                             EditArticleContentUsecase editArticleContentUsecase,
                             ArticleRepository repo
    ) {
        this.createArticleUsecase = createArticleUsecase;
        this.changeTitleUsecase = changeTitleUsecase;
        this.changeIntroductionUsecase = changeIntroductionUsecase;
        this.initiateArticleCreatingSession = initiateArticleCreatingSession;
        this.editArticleContentUsecase = editArticleContentUsecase;
        this.repo = repo;
    }

        @PostMapping()
        public ResponseEntity<ApiResponse> createArticle(@RequestBody CreateArticleCommand request){
            final CreateArticleResult result = this.createArticleUsecase.execute(request);
            final String token = this.initiateArticleCreatingSession.execute(result.title(), result.coverImageId());

            URI selfUrl = UriComponentsBuilder
                    .fromUriString("http://localhost:8080/articles")
                    .pathSegment(result.articleId())
                    .build()
                    .toUri();
            URI uploadImageUrl = UriComponentsBuilder
                    .fromUriString("http://localhost:8080/media")
                    .queryParam("session_id", token)
                    .build()
                    .toUri();

            Hyperlink selfLink = HyperlinkBuilder.get()
                    .withHref(selfUrl.toString())
                    .build();
            Hyperlink uploadImageLink = HyperlinkBuilder.put()
                    .withHref(uploadImageUrl.toString())
                    .withExpireIn(15)
                    .build();
            Hyperlink editContentLink = HyperlinkBuilder.put()
                    .withHref(selfUrl.toString() + "/content")
                    .build();

            HyperlinkCollection links = new HyperlinkCollection();
            links.addLink("self", selfLink);
            links.addLink("uploadImage", uploadImageLink);
            links.addLink("editContent", editContentLink);
            return ResponseEntity.created(selfUrl).body(
                    ApiResponse.builder()
                            .links(links)
                            .build()
            );
        }

    @PutMapping("/{id}/title")
    public ResponseEntity<ApiResponse> changeTitle(@PathVariable String id, @RequestBody ChangeTitleRequestBody body) {
        ChangeTitleCommand request = new ChangeTitleCommand(id, body.title());
        this.changeTitleUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PutMapping("/{id}/introduction")
    public ResponseEntity<ApiResponse> changeIntroduction(@PathVariable String id, @RequestBody ChangeIntroductionRequestBody body) {
        ChangeIntroductionCommand request = new ChangeIntroductionCommand(id, body.introduction());
        this.changeIntroductionUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PutMapping("/{id}/content")
    public ResponseEntity<ApiResponse> editContent(
            @PathVariable String id,
            @RequestBody ArrayList<EditBlockCommand> body)
    {
        var result = this.editArticleContentUsecase.execute(new EditArticleContentCommand(id, body));
        var response = ArticleResponse.fromDomain(result);

        String self = "http://localhost:8080/articles/" + response.id();
        String editContent = self + "/content";

        Hyperlink selfLink = HyperlinkBuilder.get()
                .withHref(self)
                .build();
        Hyperlink editContentLink = HyperlinkBuilder.put()
                .withHref(editContent)
                .build();
        HyperlinkCollection links = new HyperlinkCollection();
        links.addLink("self", selfLink);
        links.addLink("editContent", editContentLink);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .result(response.content())
                        .links(links)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable String id) {
        Article article = repo.getById(new ArticleId(id)).orElseThrow(() -> new AggregateNotFound("Article", id));
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .result(article)
                        .build()
        );
    }
}

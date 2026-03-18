package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.sync.article.change_introduction.ChangeIntroductionCommand;
import com.uit.tourism_article_management.application.command.sync.article.change_introduction.ChangeIntroductionUsecase;
import com.uit.tourism_article_management.application.command.sync.article.change_title.ChangeTitleCommand;
import com.uit.tourism_article_management.application.command.sync.article.change_title.ChangeTitleUsecase;
import com.uit.tourism_article_management.application.command.sync.article.create_article.CreateArticleCommand;
import com.uit.tourism_article_management.application.command.sync.article.create_article.CreateArticleUsecase;
import com.uit.tourism_article_management.application.command.sync.article.edit_article_content.EditArticleContentCommand;
import com.uit.tourism_article_management.application.command.sync.article.edit_article_content.EditArticleContentUsecase;
import com.uit.tourism_article_management.application.command.sync.token.initiate_article_creating_session.InitiateArticleCreatingSession;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.RecommendArticlesQuery;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.RecommendedArticleSnippet;
import com.uit.tourism_article_management.application.query.media.GetSingleArticleQuery;
import com.uit.tourism_article_management.application.command.sync.article.edit_article_content.EditBlockCommand;
import com.uit.tourism_article_management.presentation.dto.*;
import org.springframework.ai.document.Document;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final CreateArticleUsecase createArticleUsecase;
    private final ChangeTitleUsecase changeTitleUsecase;
    private final ChangeIntroductionUsecase changeIntroductionUsecase;
    private final InitiateArticleCreatingSession initiateArticleCreatingSession;
    private final EditArticleContentUsecase editArticleContentUsecase;
    private final GetSingleArticleQuery getArticle;
    private final RecommendArticlesQuery getRecommendations;

    public ArticleController(CreateArticleUsecase createArticleUsecase,
                             ChangeTitleUsecase changeTitleUsecase,
                             ChangeIntroductionUsecase changeIntroductionUsecase,
                             InitiateArticleCreatingSession initiateArticleCreatingSession,
                             EditArticleContentUsecase editArticleContentUsecase,
                             GetSingleArticleQuery getArticle,
                             RecommendArticlesQuery getRecommendations
    ) {
        this.createArticleUsecase = createArticleUsecase;
        this.changeTitleUsecase = changeTitleUsecase;
        this.changeIntroductionUsecase = changeIntroductionUsecase;
        this.initiateArticleCreatingSession = initiateArticleCreatingSession;
        this.editArticleContentUsecase = editArticleContentUsecase;
        this.getArticle = getArticle;
        this.getRecommendations = getRecommendations;
    }

        @PostMapping()
        public ResponseEntity<ApiResponse> createArticle(@RequestBody CreateArticleCommand request){
            var result = ArticleResponse.fromDomain(this.createArticleUsecase.execute(request));
            final String token = this.initiateArticleCreatingSession.execute(result.title(), result.coverImageId());

            URI selfUrl = UriComponentsBuilder
                    .fromUriString("http://localhost:8080/articles")
                    .pathSegment(result.id())
                    .build()
                    .toUri();

            Hyperlink selfLink = HyperlinkBuilder.get()
                    .withHref(selfUrl.toString())
                    .build();
            Hyperlink editContentLink = HyperlinkBuilder.put()
                    .withHref(selfUrl.toString() + "/content")
                    .build();

            HyperlinkCollection links = new HyperlinkCollection();
            links.addLink("self", selfLink);
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
    public ResponseEntity getAnArticle(@PathVariable String id) {
        var article = this.getArticle.execute(id);
        var result = ApiResponse.builder()
                .result(ArticleResponse.fromDomain(article))
                .build();

        Link getRecommendataionsLink = linkTo(methodOn(ArticleController.class).getRecommendations(id)).withRel("getRecommendations");
        EntityModel<ApiResponse> resource = EntityModel.of(result);
        resource.add(getRecommendataionsLink);

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<ApiResponse> getRecommendations(@PathVariable String id) {
        var recommended = this.getRecommendations.execute(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .result(recommended)
                        .build()
        );
    }
}

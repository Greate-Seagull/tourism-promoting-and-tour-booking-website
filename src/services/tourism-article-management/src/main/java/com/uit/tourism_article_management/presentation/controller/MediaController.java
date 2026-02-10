package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.sync.invalidate_token.InvalidateSession;
import com.uit.tourism_article_management.application.command.sync.replace_resource.ReplaceResourceCommand;
import com.uit.tourism_article_management.application.command.sync.replace_resource.ReplaceResourceUsecase;
import com.uit.tourism_article_management.application.command.sync.upload_resource.UploadResourceCommand;
import com.uit.tourism_article_management.application.command.sync.upload_resource.UploadResourceUsecase;
import com.uit.tourism_article_management.application.query.get_resource_by_id.GetResourceByIdQuery;
import com.uit.tourism_article_management.application.query.validate_article_creating_session.ArticleCreatingSessionResult;
import com.uit.tourism_article_management.application.query.validate_article_creating_session.IntrospectArticleCreatingSession;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final ReplaceResourceUsecase replaceResourceUsecase;

    private final UploadResourceUsecase uploadResourceUsecase;
    private final IntrospectArticleCreatingSession introspectArticleCreatingSession;
    private final InvalidateSession invalidateSession;

    private final GetResourceByIdQuery getResourceByIdQuery;

    public MediaController(ReplaceResourceUsecase replaceResourceUsecase, UploadResourceUsecase uploadResourceUsecase, IntrospectArticleCreatingSession introspectArticleCreatingSession, InvalidateSession invalidateSession, GetResourceByIdQuery getResourceByIdQuery) {
        this.replaceResourceUsecase = replaceResourceUsecase;
        this.uploadResourceUsecase = uploadResourceUsecase;
        this.introspectArticleCreatingSession = introspectArticleCreatingSession;
        this.invalidateSession = invalidateSession;
        this.getResourceByIdQuery = getResourceByIdQuery;
    }

    @PutMapping(consumes = "image/*")
    public ResponseEntity<ApiResponse> uploadResource(
            @RequestParam("session_id") String sessionId,
            @RequestHeader("Content-Type") MediaType contentType,
            InputStream stream
    ) {
        final ArticleCreatingSessionResult result = this.introspectArticleCreatingSession.execute(sessionId);
        final UploadResourceCommand request = new UploadResourceCommand(result.coverImageId(), result.title(), contentType.toString(), stream);
        this.uploadResourceUsecase.execute(request);
        this.invalidateSession.execute(sessionId);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @PutMapping(value = "/{id}", consumes = "image/*")
    public ResponseEntity<ApiResponse> replaceResource(@PathVariable String id,
                                                       @RequestHeader("Content-Type") MediaType contentType,
                                                       InputStream stream)
    {
        final ReplaceResourceCommand request = new ReplaceResourceCommand(id, contentType.toString(), stream);
        this.replaceResourceUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }

    @GetMapping(value = "/{id}")
    public void getResourceById(HttpServletResponse response, @PathVariable String id) throws IOException {
         this.getResourceByIdQuery.execute(response.getOutputStream(), id);
    }
}

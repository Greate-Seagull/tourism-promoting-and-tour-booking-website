package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.sync.media.upload_resource.UploadResourceCommand;
import com.uit.tourism_article_management.application.command.sync.media.upload_resource.UploadResourceUsecase;
import com.uit.tourism_article_management.application.query.media.DownloadResourceByIdQuery;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final UploadResourceUsecase uploadResourceUsecase;
    private final DownloadResourceByIdQuery downloadByIdQuery;

    public MediaController(
            UploadResourceUsecase uploadResourceUsecase,
            DownloadResourceByIdQuery downloadByIdQuery) {
        this.uploadResourceUsecase = uploadResourceUsecase;
        this.downloadByIdQuery = downloadByIdQuery;
    }

    @PostMapping(consumes = "image/*")
    public ResponseEntity<ApiResponse> upload(
            @RequestHeader("Content-Type") MediaType contentType,
            InputStream stream
    ){
        var result = this.uploadResourceUsecase.execute(new UploadResourceCommand(contentType.toString(), stream));

        URI selfLink = UriComponentsBuilder.fromUriString("http://localhost:8080")
                .pathSegment("media")
                .pathSegment(result.mediaId())
                .build()
                .toUri();

        return ResponseEntity.created(selfLink)
                .body(ApiResponse.builder()
                        .result(result)
                        .build()
                );
    }

    @GetMapping(value = "/{id}")
    public void download(HttpServletResponse response, @PathVariable String id) throws IOException {
         this.downloadByIdQuery.execute(response.getOutputStream(), id);
    }
}

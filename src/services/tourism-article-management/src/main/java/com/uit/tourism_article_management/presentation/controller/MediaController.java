package com.uit.tourism_article_management.presentation.controller;

import com.uit.tourism_article_management.application.command.replace_cover_image.ReplaceResourceCommand;
import com.uit.tourism_article_management.application.command.replace_cover_image.ReplaceResourceUsecase;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/media")
public class MediaController {
    private final ReplaceResourceUsecase replaceResourceUsecase;

    public MediaController(ReplaceResourceUsecase replaceResourceUsecase) {
        this.replaceResourceUsecase = replaceResourceUsecase;
    }

    @PutMapping(value = "/{id}", consumes = "image/*")
    public ResponseEntity<ApiResponse> replaceResource(@PathVariable String id,
                                                       @RequestHeader("Content-Type") MediaType contentType,
                                                       InputStream stream)
    {
        final ReplaceResourceCommand request = new ReplaceResourceCommand(id, contentType.getType(), stream);
        this.replaceResourceUsecase.execute(request);
        return ResponseEntity.ok(ApiResponse.builder().build());
    }
}

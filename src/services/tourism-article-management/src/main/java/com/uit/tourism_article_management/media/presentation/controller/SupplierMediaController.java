package com.uit.tourism_article_management.media.presentation.controller;

import com.uit.tourism_article_management.article.application.port.MediaStore;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.net.URI;

@RestController
@RequestMapping("/supplier/media")
public class SupplierMediaController {
    private final MediaStore mediaStore;

    public SupplierMediaController(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    @PostMapping(consumes = "image/*")
    public ResponseEntity upload(
            @RequestHeader("Content-Type") MediaType contentType,
            InputStream stream
    ) {
        var result = this.mediaStore.upload(contentType, stream);

        URI selfLink = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();

        return ResponseEntity.created(selfLink).build();
    }
}

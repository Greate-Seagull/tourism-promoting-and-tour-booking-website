package com.uit.tourism_article_management.media;

import com.uit.tourism_article_management.application.command.sync.media.upload_resource.UploadResourceCommand;
import com.uit.tourism_article_management.article.application.port.MediaStore;
import com.uit.tourism_article_management.presentation.dto.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

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
    ){
        var result = this.mediaStore.upload(contentType, stream);

        URI selfLink = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.id())
                .toUri();

        return ResponseEntity.created(selfLink).build();
    }
}

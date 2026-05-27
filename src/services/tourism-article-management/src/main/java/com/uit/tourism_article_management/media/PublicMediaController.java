package com.uit.tourism_article_management.media;

import com.uit.tourism_article_management.article.application.port.MediaStore;
import com.uit.tourism_article_management.exception.ClientException;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/public/media")
public class PublicMediaController {
    private final MediaStore mediaStore;

    public PublicMediaController(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity download(@PathVariable String id, @RequestHeader HttpHeaders header) throws IOException {
        var media = this.mediaStore.getMetadataById(id)
                .orElseThrow(() -> new ClientException("Media does not exist"));
        Resource resource = this.mediaStore.getResource(id);
        return ResponseEntity.ok()
                .contentType(media.type())
                .contentLength(resource.contentLength())
                .lastModified(media.lastModified())
                .eTag(media.checksum())
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)
                        .cachePublic()
                        .immutable()
                )
                .body(resource);
    }
}

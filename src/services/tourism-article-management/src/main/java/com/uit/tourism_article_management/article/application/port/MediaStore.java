package com.uit.tourism_article_management.article.application.port;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import java.io.InputStream;
import java.util.Optional;

public interface MediaStore {
    boolean existsById(String id);

    Optional<Media> getMetadataById(String id);

    Resource getResource(String id);

    Media upload(MediaType contentType, InputStream stream);
}

package com.uit.tourism_article_management.article.application.port;

import org.springframework.http.MediaType;

import java.time.ZonedDateTime;

public record Media(
        String id,
        MediaType type,
        ZonedDateTime lastModified,
        String checksum
) {
}

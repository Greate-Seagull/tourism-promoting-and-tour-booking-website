package com.uit.tourism_article_management.presentation.dto;

import com.uit.tourism_article_management.domain.model.media.Media;
import org.springframework.http.MediaType;

public record MediaResponse(
        String id,
        MediaType type,
        String checksum,
        long lastModified
) {

    public static MediaResponse fromDomain(Media media) {
        return new MediaResponse(
                media.getId().id(),
                MediaType.parseMediaType(media.getType().getMimeType()),
                media.getChecksum(),
                media.getUploadedAt().getTime()
        );
    }

    public boolean isStreaming() {
        return type.getType().equalsIgnoreCase("video") ||
                type.getType().equalsIgnoreCase("audio");
    }
}

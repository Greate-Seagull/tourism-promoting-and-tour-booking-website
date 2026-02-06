package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.exception.MissingFieldException;

public class Media {
    private final MediaId id;
    private MediaFile file;

    private Media(MediaId id) {
        if (id == null)
            throw new MissingFieldException("mediaId");
        this.id = id;
    }

    public static Media create(MediaId id, MediaFile file) {
        Media media = new Media(id);
        media.file = file;
        return media;
    }

    public static Media rehydrate(MediaId mediaId, MediaFile mediaFile) {
        Media media = new Media(mediaId);
        media.file = mediaFile;
        return media;
    }

    public void changeResource(MediaType mediaType, String resourceId) {
        this.file = this.file.withResource(mediaType, resourceId);
    }

    public MediaFile getFile() {
        return file;
    }

    public MediaId getId() {
        return id;
    }
}

package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.exception.MissingFieldException;
import com.uit.tourism_article_management.domain.model.AggregateRoot;

public class Media extends AggregateRoot {
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

    public void changeResource(MediaType mediaType, String newResourceId) {
        final String oldResourceId = this.file.resourceId();
        this.file = this.file.withResource(mediaType, newResourceId);

        this.apply(new ResourceChanged(id, newResourceId, oldResourceId));
    }

    public MediaFile getFile() {
        return file;
    }

    public MediaId getId() {
        return id;
    }
}

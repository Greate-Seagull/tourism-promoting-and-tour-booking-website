package com.uit.tourism_article_management.application.port.media;

public abstract class MediaStoreDecorator implements MediaStore {
    protected final MediaStore origin;

    protected MediaStoreDecorator(MediaStore origin) {
        this.origin = origin;
    }
}

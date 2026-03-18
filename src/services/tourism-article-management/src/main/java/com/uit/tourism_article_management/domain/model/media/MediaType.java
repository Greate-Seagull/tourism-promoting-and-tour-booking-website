package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.model.DomainException;

public enum MediaType {
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    ;

    private final String mimeType;

    MediaType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static MediaType rehydrate(String mimeType) {
        for(MediaType mediaType : MediaType.values()) {
            if (mediaType.getMimeType().equalsIgnoreCase(mimeType))
                return mediaType;
        }

        throw new IllegalArgumentException("Type not found: " + mimeType);
    }

    public static MediaType existing(String type) {
        for(MediaType mediaType : MediaType.values()) {
            if (mediaType.getMimeType().equalsIgnoreCase(type))
                return mediaType;
        }

        throw DomainException.unsupported(type);
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return mimeType;
    }
}

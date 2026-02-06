package com.uit.tourism_article_management.domain.model.media;


import com.uit.tourism_article_management.domain.exception.UnsupportedMimeTypeException;

public enum MediaType {
    IMAGE_JPEG("image/jpeg");

    private final String mimeType;

    MediaType(String mimeType) {
        this.mimeType = mimeType;
    }

    public static MediaType fromString(String mimeType) {
        for(MediaType mediaType : MediaType.values()) {
            if (mediaType.getMimeType().equalsIgnoreCase(mimeType))
                return mediaType;
        }

        throw new UnsupportedMimeTypeException(mimeType);
    }

    public String getMimeType() {
        return mimeType;
    }

    @Override
    public String toString() {
        return mimeType;
    }
}

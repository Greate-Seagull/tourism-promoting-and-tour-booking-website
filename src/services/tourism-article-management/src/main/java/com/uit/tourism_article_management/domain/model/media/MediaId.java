package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.model.DomainException;

public record MediaId(String id) {
    public MediaId {
        if(id == null)
            throw new IllegalArgumentException("mediaId cannot be null");
    }

    public static MediaId existing(String id) {
        if(id == null)
            throw DomainException.missing("media id");
        return new MediaId(id);
    }

    @Override
    public String toString() {
        return id;
    }
}

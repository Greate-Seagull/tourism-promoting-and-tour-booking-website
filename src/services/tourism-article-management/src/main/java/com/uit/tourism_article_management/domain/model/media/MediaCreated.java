package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.event.DomainEvent;

public class MediaCreated extends DomainEvent {
    private final MediaId id;

    public MediaCreated(MediaId id) {
        this.id = id;
    }

    public MediaId getId() {
        return id;
    }
}

package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.event.DomainEvent;

public class ResourceChanged extends DomainEvent {
    private MediaId mediaId;
    private String newResourceId;
    private String oldResourceId;

    public ResourceChanged(MediaId mediaId, String newResourceId, String oldResourceId) {
        this.mediaId = mediaId;
        this.newResourceId = newResourceId;
        this.oldResourceId = oldResourceId;
    }

    public MediaId getMediaId() {
        return mediaId;
    }

    public String getNewResourceId() {
        return newResourceId;
    }

    public String getOldResourceId() {
        return oldResourceId;
    }
}

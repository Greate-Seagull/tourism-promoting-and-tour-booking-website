package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.event.DomainEvent;
import com.uit.tourism_article_management.domain.model.media.MediaId;

public class ArticleCreated extends DomainEvent {
    private ArticleId id;
    private MediaId coverImageId;

    public ArticleCreated(ArticleId id, MediaId coverImageId) {
        this.id = id;
        this.coverImageId = coverImageId;
    }

    public MediaId getCoverImageId() {
        return coverImageId;
    }

    public ArticleId getId() {
        return id;
    }
}
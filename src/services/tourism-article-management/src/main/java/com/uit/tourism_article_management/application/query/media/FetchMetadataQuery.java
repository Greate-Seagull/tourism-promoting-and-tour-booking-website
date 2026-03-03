package com.uit.tourism_article_management.application.query.media;

import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Component;

@Component
public class FetchMetadataQuery {
    private final MediaStore store;

    public FetchMetadataQuery(MediaStore store) {
        this.store = store;
    }

    public Media execute(String id) {
        return this.store.getById(MediaId.existing(id))
                .orElseThrow(() -> new AggregateNotFound("Media", id));
    }
}

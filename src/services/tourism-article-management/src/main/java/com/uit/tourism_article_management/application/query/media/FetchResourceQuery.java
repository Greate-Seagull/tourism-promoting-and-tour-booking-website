package com.uit.tourism_article_management.application.query.media;

import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.io.InputStream;

public class FetchResourceQuery {
    private final MediaStore store;

    public FetchResourceQuery(MediaStore store) {
        this.store = store;
    }

    public InputStream execute(String id) {
        return this.store.download(MediaId.existing(id));
    }
}

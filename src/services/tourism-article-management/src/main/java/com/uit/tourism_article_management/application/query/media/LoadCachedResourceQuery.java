package com.uit.tourism_article_management.application.query.media;

import com.uit.tourism_article_management.application.port.media.CacheStore;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Component;

@Component
public class LoadCachedResourceQuery {
    private final CacheStore cache;

    public LoadCachedResourceQuery(CacheStore cache) {
        this.cache = cache;
    }

    public String execute(String id) {
        return this.cache.getCachePath(MediaId.existing(id));
    }
}

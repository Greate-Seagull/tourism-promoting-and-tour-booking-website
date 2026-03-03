package com.uit.tourism_article_management.application.port.media;

import com.uit.tourism_article_management.domain.model.media.MediaId;

public interface CacheStore {
    String getCachePath(MediaId id);
}

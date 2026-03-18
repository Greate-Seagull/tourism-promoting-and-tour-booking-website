package com.uit.tourism_article_management.application.port.media;

import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaReference;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface MediaReferenceRepository {
    Optional<MediaReference> getById(MediaId id);
    Collection<MediaReference> getManyById(Collection<MediaId> ids);
    void saveMany(Collection<MediaReference> references);
    void save(MediaReference reference);
    Collection<MediaId> getOrphans(OffsetDateTime threshold, int limit);
    void deleteManyById(Collection<MediaId> ids);
}

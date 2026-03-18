package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.model.DomainException;

import java.time.OffsetDateTime;

public class MediaReference {
    private static final int GRACE_PERIOD_HOURS = 24;

    private final MediaId id;
    private int count;
    private OffsetDateTime updatedAt;

    private MediaReference(MediaId id) {
        this.id = id;
    }

    public static MediaReference rehydrate(MediaId mediaId, int count, OffsetDateTime updatedAt) {
        MediaReference mediaReference = new MediaReference(mediaId);
        mediaReference.count = count;
        mediaReference.updatedAt = updatedAt;
        return mediaReference;
    }

    public static MediaReference initiate(MediaId id) {
        MediaReference mediaReference = new MediaReference(id);
        mediaReference.count = 0;
        mediaReference.updatedAt = OffsetDateTime.now();
        return mediaReference;
    }

    public static OffsetDateTime getStaleThreshold() {
        return OffsetDateTime.now().minusHours(GRACE_PERIOD_HOURS);
    }

    public void adjustCount(int value) {
        if(this.count + value < 0)
            throw DomainException.insufficient("reference count", -value, this.count);

        this.count += value;
        this.updatedAt = OffsetDateTime.now();
    }

    public MediaId getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}

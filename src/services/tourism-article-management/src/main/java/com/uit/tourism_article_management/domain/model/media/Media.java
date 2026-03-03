package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.model.AggregateRoot;

import java.util.Date;

public class Media extends AggregateRoot {
    private final MediaId id;
    private final String checksum;
    private MediaName name;
    private MediaType type;
    private Date uploadedAt;

    public Media(MediaId id, String checksum, MediaType type) {
        this.id = id;
        this.checksum = checksum;
        this.name = MediaName.generateName();
        this.type = type;
        this.uploadedAt = new Date();

        this.apply(new MediaCreated(this.id));
    }

    public Media(MediaId id, String checksum) {
        this.id = id;
        this.checksum = checksum;
    }

    public static Media rehydrate(MediaId id, String checksum, MediaName name, MediaType type, Date uploadedAt) {
        var media = new Media(id, checksum);
        media.name = name;
        media.type = type;
        media.uploadedAt = uploadedAt;
        return media;
    }

    public MediaId getId() {
        return id;
    }

    public MediaName getName() {
        return name;
    }

    public MediaType getType() {
        return type;
    }

    public String getChecksum() {
        return checksum;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }
}

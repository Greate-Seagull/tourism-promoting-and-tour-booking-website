package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.model.AggregateRoot;

public class Media extends AggregateRoot {
    private final MediaId id;
    private final String checksum;
    private MediaName name;
    private MediaType type;

    public Media(MediaId id, String checksum, MediaType type) {
        this.id = id;
        this.checksum = checksum;
        this.name = MediaName.generateName();
        this.type = type;

        this.apply(new MediaCreated(this.id));
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
}

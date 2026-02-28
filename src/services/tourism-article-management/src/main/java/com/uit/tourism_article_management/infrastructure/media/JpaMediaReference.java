package com.uit.tourism_article_management.infrastructure.media;

import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "media_references")
@Getter
@Setter
public class JpaMediaReference {
    @Id
    private String mediaId;
    private int count;
    private OffsetDateTime updatedAt;

    public MediaReference toDomain() {
        return MediaReference.rehydrate(
                MediaId.existing(mediaId),
                count,
                updatedAt
        );
    }

    public static JpaMediaReference fromDomain(MediaReference mediaReference) {
        var persistence = new JpaMediaReference();
        persistence.mediaId = mediaReference.getId().id();
        persistence.count = mediaReference.getCount();
        persistence.updatedAt = mediaReference.getUpdatedAt();
        return persistence;
    }
}

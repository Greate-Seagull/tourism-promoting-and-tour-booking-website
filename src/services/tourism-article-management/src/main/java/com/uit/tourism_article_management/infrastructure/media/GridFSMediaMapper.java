package com.uit.tourism_article_management.infrastructure.media;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaName;
import com.uit.tourism_article_management.domain.model.media.MediaType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GridFSMediaMapper {
    public Optional<Media> toDomain(GridFSFile file) {
        if(file == null) return Optional.empty();
        return Optional.of(Media.rehydrate(
                new MediaId(file.getObjectId().toHexString()),
                file.getMetadata().get("checksum").toString(),
                new MediaName(file.getFilename()),
                MediaType.rehydrate(file.getMetadata().get("media_type").toString()),
                file.getUploadDate()
        ));
    }
}

package com.uit.tourism_article_management.infrastructure.media;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaFile;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {
    public Media toDomain(GridFSFile gridFSFile) {
        if (gridFSFile == null)
            return null;

        return Media.rehydrate(
                new MediaId(gridFSFile.getMetadata().get("media_id").toString()),
                new MediaFile(
                        gridFSFile.getFilename(),
                        MediaType.fromString(gridFSFile.getMetadata().get("media_type").toString()),
                        gridFSFile.getObjectId().toHexString()
                )
        );
    }
}

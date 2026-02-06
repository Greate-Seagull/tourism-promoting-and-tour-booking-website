package com.uit.tourism_article_management.infrastructure.media;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.uit.tourism_article_management.application.port.MediaStore;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Optional;

@Component
public class GridFSMediaStore implements MediaStore {
    private final GridFSBucket gridBucket;
    private final MediaMapper mapper;

    public GridFSMediaStore(GridFSBucket gridBucket, MediaMapper mapper) {
        this.gridBucket = gridBucket;
        this.mapper = mapper;
    }

    @Override
    public String nextResourceIdentity() {
        return new ObjectId().toHexString();
    }

    @Override
    public Optional<Media> getById(MediaId mediaId) {
        final GridFSFile file = this.gridBucket.find(
                Filters.eq("media_id", mediaId.id())
        ).first();

        return Optional.ofNullable(mapper.toDomain(file));
    }

    @Override
    public void replace(Media media, InputStream stream) {
        this.store(media, stream);
        this.deleteByResourceId(media.getFile().resourceId());
    }

    private void deleteByResourceId(String resourceId) {
        this.gridBucket.delete(new ObjectId(resourceId));
    }

    private void store(Media media, InputStream stream) {
        this.gridBucket.uploadFromStream(
                new BsonObjectId(new ObjectId(media.getFile().resourceId())),
                media.getFile().name(),
                stream,
                new GridFSUploadOptions().metadata(
                        new Document()
                                .append("media_id", media.getId().toString())
                                .append("media_type", media.getFile().type().toString())
                )
        );
    }
}

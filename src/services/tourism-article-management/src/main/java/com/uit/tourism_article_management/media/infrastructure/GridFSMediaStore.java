package com.uit.tourism_article_management.media.infrastructure;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.infrastructure.media.GridFSMediaMapper;
import com.uit.tourism_article_management.media.domain.Media;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("original")
public class GridFSMediaStore implements MediaStore {
    private final GridFSBucket gridBucket;
    private final GridFSMediaMapper mapper;

    public GridFSMediaStore(
            GridFSBucket gridBucket,
            GridFSMediaMapper mapper
    ) {
        this.gridBucket = gridBucket;
        this.mapper = mapper;
    }

    @Override
    public MediaId nextIdentity() {
        return new MediaId(new ObjectId().toHexString());
    }

    @Override
    public void upload(Media media, InputStream stream) {
        this.gridBucket.uploadFromStream(
                new BsonObjectId(new ObjectId(media.getId().id())),
                media.getName().name(),
                stream,
                new GridFSUploadOptions().metadata(
                        new Document()
                                .append("media_type", media.getType().getMimeType())
                                .append("checksum", media.getChecksum())
                )
        );
    }

    @Override
    public void download(MediaId id, OutputStream response) {
        this.gridBucket.downloadToStream(new ObjectId(id.id()), response);
    }

    @Override
    public void deleteById(MediaId id) {
        this.gridBucket.delete(new ObjectId(id.id()));
    }

    @Override
    public Optional<MediaId> getIdByChecksum(String checksum) {
        final GridFSFile file = this.gridBucket
                .find(Filters.eq("metadata.checksum", checksum))
                .first();

        return Optional.ofNullable(file)
                .map(f -> new MediaId(file.getObjectId().toHexString()));
    }

    @Override
    public void deleteManyById(Collection<MediaId> ids) {
        ids.parallelStream().forEach(id -> this.gridBucket.delete(new ObjectId(id.id())));
    }

    @Override
    public Optional<Media> getById(MediaId id) {
        return this.mapper.toDomain(
                this.gridBucket.find(Filters.eq("_id", new ObjectId(id.id())))
                        .first()
        );
    }

    public InputStream download(MediaId id) {
        return this.gridBucket.openDownloadStream(new ObjectId(id.id()));
    }
}

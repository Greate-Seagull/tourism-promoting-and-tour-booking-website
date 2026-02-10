package com.uit.tourism_article_management.application.command.sync.upload_resource;

import com.uit.tourism_article_management.application.port.MediaStore;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaFile;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaType;
import org.springframework.stereotype.Service;

@Service
public class UploadResourceUsecase {
    private final MediaStore mediaStore;

    public UploadResourceUsecase(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    public void execute(UploadResourceCommand command) {
        final MediaId mediaId = new MediaId(command.mediaId());
        final MediaFile mediaFile = new MediaFile(
                command.filename(),
                MediaType.fromString(command.mimeType()),
                this.mediaStore.nextResourceIdentity()
        );
        final Media media = Media.create(mediaId, mediaFile);

        this.mediaStore.upload(media, command.stream());
    }
}
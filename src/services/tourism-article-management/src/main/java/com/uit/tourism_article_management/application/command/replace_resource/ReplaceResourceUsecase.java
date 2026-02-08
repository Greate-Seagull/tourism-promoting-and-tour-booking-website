package com.uit.tourism_article_management.application.command.replace_resource;

import com.uit.tourism_article_management.application.port.MediaStore;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaType;
import org.springframework.stereotype.Service;

@Service
public class ReplaceResourceUsecase {
    private final MediaStore mediaStore;

    public ReplaceResourceUsecase(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    public void execute(ReplaceResourceCommand command) {
        final MediaId mediaId = new MediaId(command.mediaId());
        final Media media = this.mediaStore.getById(mediaId)
                .orElseThrow(() -> new AggregateNotFound("Media", command.mediaId()));

        media.changeResource(
            MediaType.fromString(command.mimeType()),
            this.mediaStore.nextResourceIdentity()
        );

        this.mediaStore.replace(media, command.stream());
    }
}

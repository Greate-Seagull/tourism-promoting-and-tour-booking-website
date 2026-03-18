package com.uit.tourism_article_management.application.command.sync.media.upload_resource;

import com.uit.tourism_article_management.application.port.media.MediaProcessor;
import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.event.DomainEventPublisher;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UploadResourceUsecase {
    private final MediaStore mediaStore;
    private final MediaProcessor mediaProcessor;
    private final DomainEventPublisher eventPublisher;

    public UploadResourceUsecase(
            MediaStore mediaStore,
            MediaProcessor mediaProcessor,
            DomainEventPublisher eventPublisher
    ) {
        this.mediaStore = mediaStore;
        this.mediaProcessor = mediaProcessor;
        this.eventPublisher = eventPublisher;
    }

    public UploadResourceResult execute(UploadResourceCommand command) {
        try {
            return this.mediaProcessor.process(
                    command.stream(),
                    (buffered, checksum) -> {
                        Optional<MediaId> existedId = this.mediaStore.getIdByChecksum(checksum);
                        if(existedId.isPresent()) {
                            return new UploadResourceResult(existedId.get().id());
                        }

                        final Media media = new Media(
                                this.mediaStore.nextIdentity(),
                                checksum,
                                MediaType.rehydrate(command.mimeType())
                        );

                        this.mediaStore.upload(media, buffered);

                        this.eventPublisher.publish(media.getEvents());

                        return new UploadResourceResult(media.getId().id());
                    });

        } catch(IOException e) {
            throw new RuntimeException("Upload failed", e);
        }
    }
}
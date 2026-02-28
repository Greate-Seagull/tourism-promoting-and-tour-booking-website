package com.uit.tourism_article_management.application.command.async.job.media;

import com.uit.tourism_article_management.application.port.media.MediaReferenceRepository;
import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.model.media.MediaReference;
import org.springframework.stereotype.Component;

@Component
public class CleanUpOrphanMedia {
    private final MediaStore mediaStore;
    private final MediaReferenceRepository referenceRepository;

    public CleanUpOrphanMedia(
            MediaStore mediaStore,
            MediaReferenceRepository referenceRepository
    ) {
        this.mediaStore = mediaStore;
        this.referenceRepository = referenceRepository;
    }

    public int execute() {
        var references = this.referenceRepository.getOrphans(MediaReference.getStaleThreshold(), 500);
        if (references.isEmpty())
            return 0;

        this.mediaStore.deleteManyById(references);
        this.referenceRepository.deleteManyById(references);

        return references.size();
    }
}

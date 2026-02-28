package com.uit.tourism_article_management.application.command.async.event.media;

import com.uit.tourism_article_management.application.port.media.MediaReferenceRepository;
import com.uit.tourism_article_management.domain.event.EventHandler;
import com.uit.tourism_article_management.domain.model.media.MediaCreated;
import com.uit.tourism_article_management.domain.model.media.MediaReference;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitiateMediaReference implements EventHandler<MediaCreated> {
    private final MediaReferenceRepository repo;

    public InitiateMediaReference(MediaReferenceRepository repo) {
        this.repo = repo;
    }

    @Override
    @EventListener
    public void handle(MediaCreated event) {
        var reference = MediaReference.initiate(event.getId());
        this.repo.save(reference);
    }
}

package com.uit.tourism_article_management.application.command.async.delete_resource;

import com.uit.tourism_article_management.application.port.MediaStore;
import com.uit.tourism_article_management.domain.event.EventHandler;
import com.uit.tourism_article_management.domain.model.media.ResourceChanged;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DeleteResourceEventHandler implements EventHandler<ResourceChanged> {
    private final MediaStore mediaStore;

    public DeleteResourceEventHandler(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    @Override
    @EventListener
    public void handle(ResourceChanged event) {
        this.mediaStore.deleteByResourceId(event.getOldResourceId());
    }
}

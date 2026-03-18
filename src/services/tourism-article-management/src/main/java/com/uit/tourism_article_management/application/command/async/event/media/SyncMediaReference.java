package com.uit.tourism_article_management.application.command.async.event.media;

import com.uit.tourism_article_management.application.port.media.MediaReferenceRepository;
import com.uit.tourism_article_management.domain.event.EventHandler;
import com.uit.tourism_article_management.domain.model.article.ArticleContentEdited;
import com.uit.tourism_article_management.domain.model.article.ArticleCreated;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaReference;
import com.uit.tourism_article_management.domain.model.media.MediaService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SyncMediaReference { //implements EventHandler<ArticleContentEdited>, EventHandler<ArticleCreated> {
    private final MediaService service;
    private final MediaReferenceRepository repo;

    public SyncMediaReference(
            MediaService service,
            MediaReferenceRepository repo
    ) {
        this.service = service;
        this.repo = repo;
    }

//    @Override
    @EventListener
    public void handle(ArticleContentEdited event) {
        var mediaChanges = this.service.analyzeChanges(event);
        var references = this.repo.getManyById(mediaChanges.keySet());

        var referenceMap = new HashMap<MediaId, MediaReference>();
        for(var reference: references) referenceMap.put(reference.getId(), reference);

        for (var entry : mediaChanges.entrySet()){
            var reference = referenceMap.get(entry.getKey());
            if(reference == null)
                // Should log before continue
                continue;
            reference.adjustCount(entry.getValue());
        }

        this.repo.saveMany(references);
    }

//    @Override
    @EventListener
    public void handle(ArticleCreated event) {
        var result = this.repo.getById(event.getCoverImageId());
        if(result.isEmpty())
            // Should log before return
            return;

        var reference = result.get();
        reference.adjustCount(1);
        this.repo.save(reference);
    }
}

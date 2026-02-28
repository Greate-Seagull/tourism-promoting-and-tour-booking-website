package com.uit.tourism_article_management.domain.model.media;

import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.article.ArticleContentEdited;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MediaService {
    public Map<MediaId, Integer> analyzeChanges(ArticleContentEdited event) {
        var oldMedia = this.extractMedia(event.getOldContent());
        var newMedia = this.extractMedia(event.getNewContent());
        var mediaReferences = new HashMap<MediaId, Integer>();
        for(var media: newMedia) mediaReferences.put(media, mediaReferences.getOrDefault(media, 0) + 1);
        for(var media: oldMedia) mediaReferences.put(media, mediaReferences.getOrDefault(media, 0) - 1);
        mediaReferences.entrySet().removeIf(entry -> entry.getValue() == 0);

        return mediaReferences;
    }

    private @NonNull Collection<MediaId> extractMedia(Collection<ArticleBlock> event) {
        return event.stream()
                .map(block -> block.content().mediaId())
                .flatMap(Optional::stream)
                .toList();
    }
}

package com.uit.tourism_article_management.application.query.media;

import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class StreamResourceQuery {
    private final MediaStore mediaStore;

    public StreamResourceQuery(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    public void execute(String mediaId, OutputStream response) {
        try{
            this.mediaStore.download(new MediaId(mediaId), response);
        } catch (RuntimeException e) {
            throw new AggregateNotFound("Media", mediaId);
        }
    }
}

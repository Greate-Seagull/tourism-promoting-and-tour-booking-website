package com.uit.tourism_article_management.application.query.media;

import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class DownloadResourceByIdQuery {
    private final MediaStore mediaStore;

    public DownloadResourceByIdQuery(MediaStore mediaStore) {
        this.mediaStore = mediaStore;
    }

    public void execute(OutputStream response, String mediaId) {
        try{
            this.mediaStore.download(response, new MediaId(mediaId));
        } catch (RuntimeException e) {
            throw new AggregateNotFound("Media", mediaId);
        }
    }
}

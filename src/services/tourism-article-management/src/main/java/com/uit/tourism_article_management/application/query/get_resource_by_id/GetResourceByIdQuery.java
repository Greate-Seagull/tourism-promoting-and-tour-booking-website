package com.uit.tourism_article_management.application.query.get_resource_by_id;

import com.uit.tourism_article_management.application.port.MediaStore;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class GetResourceByIdQuery {
    private final MediaStore mediaStore;

    public GetResourceByIdQuery(MediaStore mediaStore) {
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

package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public interface MediaStore {
    String nextResourceIdentity();
    Optional<Media> getById(MediaId mediaId);
    void replace(Media media, InputStream stream);
    void upload(Media media, InputStream stream);
    void download(OutputStream response, String resourceId);
    void download(OutputStream response, MediaId mediaId);
    void deleteByResourceId(String resourceId);
}

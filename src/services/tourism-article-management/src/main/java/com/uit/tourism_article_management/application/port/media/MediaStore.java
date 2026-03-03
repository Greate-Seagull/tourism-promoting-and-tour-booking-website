package com.uit.tourism_article_management.application.port.media;

import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import javax.swing.text.html.Option;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Optional;

public interface MediaStore {
    MediaId nextIdentity();
    void upload(Media media, InputStream stream);
    void download(MediaId id, OutputStream response);
    void deleteById(MediaId id);
    Optional<MediaId> getIdByChecksum(String checksum);

    void deleteManyById(Collection<MediaId> mediaIdStream);

    Optional<Media> getById(MediaId existing);
    InputStream download(MediaId existing);
}

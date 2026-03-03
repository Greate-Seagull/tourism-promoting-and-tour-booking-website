package com.uit.tourism_article_management.infrastructure.media;

import com.uit.tourism_article_management.application.port.media.CacheStore;
import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.application.port.media.MediaStoreDecorator;
import com.uit.tourism_article_management.domain.model.media.Media;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

//@Component
@Primary
public class DiskCachingMediaStore extends MediaStoreDecorator implements CacheStore {
    private final Path cacheRoot;
    private ConcurrentHashMap<String, Object> loadingLocks = new ConcurrentHashMap<>();

    protected DiskCachingMediaStore(
            @Qualifier("original") MediaStore origin,
            @Value("${spring.storage.cache-root}") Path cacheRoot
    ) throws IOException {
        super(origin);
        this.cacheRoot = cacheRoot.toAbsolutePath().normalize();
        Files.createDirectories(cacheRoot);
    }

    @Override
    public MediaId nextIdentity() {
        return this.origin.nextIdentity();
    }

    @Override
    public void upload(Media media, InputStream stream) {
        this.origin.upload(media, stream);
    }

    @Override
    public void download(MediaId id, OutputStream response) {
        try {
            Files.copy(this.load(id), response);
        } catch (IOException e) {
            throw new RuntimeException("Download failed", e);
        }
    }

    @Override
    public void deleteById(MediaId id) {
        this.origin.deleteById(id);
    }

    @Override
    public Optional<MediaId> getIdByChecksum(String checksum) {
        return this.origin.getIdByChecksum(checksum);
    }

    @Override
    public void deleteManyById(Collection<MediaId> ids) {
        this.origin.deleteManyById(ids);
    }

    @Override
    public Optional<Media> getById(MediaId existing) {
        return this.origin.getById(existing);
    }

    @Override
    public InputStream download(MediaId id) {
        try {
            return Files.newInputStream(this.load(id));
        } catch (IOException e) {
            throw new RuntimeException("Download failed", e);
        }
    }

    @Override
    public String getCachePath(MediaId id) {
        return this.load(id).toUri().toString();
    }

    private Path load(MediaId id) {
        try {
            String cacheFilename = id.id() + ".original";
            Path cachePath = this.cacheRoot.resolve(cacheFilename);
            if (!Files.exists(cachePath)) {
                Object lock = this.loadingLocks.computeIfAbsent(cacheFilename, key -> new Object());
                synchronized (lock) {
                    try {
                        if (!Files.exists(cachePath)) {
                            Path tempPath = cachePath.resolveSibling(cacheFilename + ".tmp");
                            try (var stream = Files.newOutputStream(tempPath)) {
                                this.origin.download(id, stream);
                            } catch (IOException e) {
                                Files.deleteIfExists(tempPath);
                                throw e;
                            }
                            Files.move(tempPath, cachePath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                    finally {
                        this.loadingLocks.remove(cacheFilename);
                    }
                }
            }

            return cachePath;
        } catch (IOException e) {
            throw new RuntimeException("Download failed", e);
        }
    }
}

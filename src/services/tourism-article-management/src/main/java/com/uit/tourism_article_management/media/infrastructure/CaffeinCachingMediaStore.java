package com.uit.tourism_article_management.media.infrastructure;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.uit.tourism_article_management.application.port.media.CacheStore;
import com.uit.tourism_article_management.application.port.media.MediaStore;
import com.uit.tourism_article_management.application.port.media.MediaStoreDecorator;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.media.domain.Media;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Component
@Primary
public class CaffeinCachingMediaStore extends MediaStoreDecorator implements CacheStore {
    private static final String fileExtension = ".original";

    private final LoadingCache<MediaId, Path> cache;
    private final Path cacheRoot;
    private final Duration diskTtl;

    protected CaffeinCachingMediaStore(
            @Qualifier("original") MediaStore origin,
            @Value("${spring.storage.cache-root}") Path cacheRoot,
            @Value("${spring.storage.disk-ttl-hours}") Duration diskTtl
    ) {
        super(origin);
        this.cacheRoot = cacheRoot;
        this.diskTtl = diskTtl;
        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(1, TimeUnit.HOURS)
                .maximumSize(1000) // 1000 keys limit
                .removalListener(this::onEviction)
                .build(this::fetchToDisk);
    }

    private Path fetchToDisk(MediaId key) throws IOException {
        Path cachePath = this.cacheRoot.resolve(key.id() + fileExtension);
        if (Files.exists(cachePath))
            return cachePath;

        Path tempPath = cachePath.resolveSibling(".tmp");
        try (OutputStream stream = Files.newOutputStream(tempPath)) {
            this.origin.download(key, stream);
        } catch (IOException e) {
            this.safeDelete(tempPath);
            throw e;
        }

        Files.move(tempPath, cachePath,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE
        );

        return cachePath;
    }

    /**
     * Called by Caffeine whenever an entry is removed from the in-memory cache.
     * We do NOT delete the file here — we keep files on disk until the TTL job
     * runs, so that a simple memory eviction doesn't force a re-download.
     */
    private void onEviction(Object o, Object o1, RemovalCause removalCause) {
        // Should log here
    }

    /**
     * Scheduled job that deletes disk files older than {@code spring.storage.disk-ttl-hours}.
     * Separating disk TTL from memory TTL gives explicit control over both resources.
     */
    @Scheduled(fixedDelayString = "${spring.storage.cleanup-interval-ms}")
    private void evictStaleDiskResources() {
        var cutoff = Instant.now().minus(diskTtl);
        try (Stream<Path> paths = Files.list(cacheRoot)) {
            paths.filter(p -> p.endsWith(fileExtension))
                    .filter(p -> this.isOlderThan(p, cutoff))
                    .forEach(this::safeDelete);
        } catch (IOException ignored) {

        }
    }

    private boolean isOlderThan(Path path, Instant cutoff) {
        try {
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);
            return attributes.lastAccessTime().toInstant().isBefore(cutoff);
        } catch (IOException e) {
            return false;
        }
    }

    private void safeDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {

        }
    }

    @Override
    public String getCachePath(MediaId id) {
        return cache.get(id).toUri().toString();
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
            Files.copy(this.cache.get(id), response);
        } catch (IOException e) {
            throw new RuntimeException("Download failed", e);
        }
    }

    @Override
    public void deleteById(MediaId id) {
        this.origin.deleteById(id);
//        this.safeDelete(this.cache.get(id));
    }

    @Override
    public Optional<MediaId> getIdByChecksum(String checksum) {
        return this.origin.getIdByChecksum(checksum);
    }

    @Override
    public void deleteManyById(Collection<MediaId> ids) {
        this.origin.deleteManyById(ids);
//        ids.parallelStream().forEach(id -> this.safeDelete(this.cache.get(id)));
    }

    @Override
    public Optional<Media> getById(MediaId id) {
        return this.origin.getById(id);
    }

    @Override
    public InputStream download(MediaId id) {
        try {
            return Files.newInputStream(this.cache.get(id));
        } catch (IOException e) {
            throw new RuntimeException("Download failed", e);
        }
    }
}
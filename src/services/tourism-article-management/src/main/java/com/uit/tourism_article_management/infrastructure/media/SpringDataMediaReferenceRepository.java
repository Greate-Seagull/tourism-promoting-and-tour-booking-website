package com.uit.tourism_article_management.infrastructure.media;

import com.uit.tourism_article_management.application.port.media.MediaReferenceRepository;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import com.uit.tourism_article_management.domain.model.media.MediaReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Collection;

@Repository
public class SpringDataMediaReferenceRepository implements MediaReferenceRepository {
    private final JpaMediaReferenceRepository repository;

    public SpringDataMediaReferenceRepository(JpaMediaReferenceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<MediaReference> getManyById(Collection<MediaId> ids) {
        return this.repository.findAllById(ids.stream().map(MediaId::id).toList())
                .stream().map(JpaMediaReference::toDomain).toList();
    }

    @Override
    public void saveMany(Collection<MediaReference> references) {
        references.forEach(this::save);
    }

    @Override
    public void save(MediaReference reference) {
        this.repository.save(JpaMediaReference.fromDomain(reference));
    }

    @Override
    public Collection<MediaId> getOrphans(OffsetDateTime threshold, int limit) {
        return this.repository.findOrphans(threshold, PageRequest.ofSize(limit))
                .getContent().stream()
                .map(MediaId::existing)
                .toList();
    }

    @Override
    public void deleteManyById(Collection<MediaId> ids) {
        this.repository.deleteAllById(ids.stream()
                .map(MediaId::id)
                .toList()
        );
    }
}

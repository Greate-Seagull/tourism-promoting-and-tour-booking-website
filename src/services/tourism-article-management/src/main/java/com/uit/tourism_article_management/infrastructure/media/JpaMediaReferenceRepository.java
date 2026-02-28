package com.uit.tourism_article_management.infrastructure.media;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface JpaMediaReferenceRepository extends JpaRepository<JpaMediaReference, String> {
    @Query("SELECT r.mediaId " +
            "FROM JpaMediaReference r " +
            "WHERE r.count = 0 AND r.updatedAt < :threshold")
    Slice<String> findOrphans(@Param("threshold") OffsetDateTime threshold, Pageable pageable);
}

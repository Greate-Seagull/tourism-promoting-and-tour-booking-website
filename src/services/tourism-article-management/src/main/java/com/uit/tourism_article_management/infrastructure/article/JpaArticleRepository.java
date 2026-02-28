package com.uit.tourism_article_management.infrastructure.article;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaArticleRepository extends JpaRepository<JpaArticle, String> {
    @Override
    @EntityGraph(attributePaths = {"content"})
    Optional<JpaArticle> findById(String s);
}

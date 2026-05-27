package com.uit.tourism_article_management.article.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JpaArticleRepository extends JpaRepository<JpaArticle, String>, QuerydslPredicateExecutor<JpaArticle> {
}

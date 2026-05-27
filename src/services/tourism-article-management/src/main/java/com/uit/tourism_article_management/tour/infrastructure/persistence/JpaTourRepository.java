package com.uit.tourism_article_management.tour.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JpaTourRepository extends JpaRepository<JpaTour, String>, QuerydslPredicateExecutor<JpaTour> {
}

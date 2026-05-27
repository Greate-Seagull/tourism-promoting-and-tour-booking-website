package com.uit.tourism_article_management.order.infrastructure.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JpaOrderRepository extends JpaRepository<JpaOrder, String>, QuerydslPredicateExecutor<JpaOrder> {
}

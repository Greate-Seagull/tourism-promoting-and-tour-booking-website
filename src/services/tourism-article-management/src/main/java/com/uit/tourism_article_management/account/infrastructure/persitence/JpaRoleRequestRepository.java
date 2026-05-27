package com.uit.tourism_article_management.account.infrastructure.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface JpaRoleRequestRepository extends JpaRepository<JpaRoleRequest, Long>, QuerydslPredicateExecutor<JpaRoleRequest> {
}

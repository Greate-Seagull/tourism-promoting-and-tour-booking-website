package com.uit.tourism_article_management.account.domain;

public record AdminAction(
        String adminId,
        Action action,
        String accountId,
        String reason
) {
}

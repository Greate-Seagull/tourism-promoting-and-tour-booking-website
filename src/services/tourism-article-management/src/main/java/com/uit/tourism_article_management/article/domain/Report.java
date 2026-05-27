package com.uit.tourism_article_management.article.domain;

import com.uit.tourism_article_management.exception.ClientException;

public record Report(
        String reason,
        String articleId,
        String accountId
) {
    static public void requireReason(String reason) {
        if (reason == null || reason.isBlank())
            throw new ClientException("Report reason must be provided");
    }
}

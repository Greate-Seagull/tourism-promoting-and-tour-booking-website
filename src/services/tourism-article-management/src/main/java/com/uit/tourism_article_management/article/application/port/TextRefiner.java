package com.uit.tourism_article_management.article.application.port;

public interface TextRefiner {
    String sanitize(String raw);

    String extractText(String raw);
}

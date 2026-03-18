package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.model.article.ArticleId;

public interface VectorStoreStatsRepository {
    void saveChunkCount(ArticleId id, int size);
    double getAverageChunkCount();
}

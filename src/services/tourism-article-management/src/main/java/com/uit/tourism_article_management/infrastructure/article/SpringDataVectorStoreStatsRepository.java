package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.port.VectorStoreStatsRepository;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringDataVectorStoreStatsRepository implements VectorStoreStatsRepository {
    private final JdbcTemplate jdbc;

    public SpringDataVectorStoreStatsRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void saveChunkCount(ArticleId id, int size) {
        String sql = "INSERT INTO article_vector_stats (article_id, chunk_count) " +
                "VALUES (?, ?) " +
                "ON CONFLICT (article_id) " +
                "DO UPDATE SET chunk_count = EXCLUDED.chunk_count";
        this.jdbc.update(sql, id.id(), size);
    }

    @Override
    public double getAverageChunkCount() {
        String sql = "SELECT AVG(chunk_count) " +
                "FROM article_vector_stats";
        var average = jdbc.queryForObject(sql, Double.class);
        return average != null ? average : 0.0;
    }
}

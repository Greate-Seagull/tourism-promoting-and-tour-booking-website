package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.application.port.VectorStoreStatsRepository;
import com.uit.tourism_article_management.application.port.article.ArticleRecommender;
import com.uit.tourism_article_management.application.query.article.article_recommendations_query.ArticleForRecommendation;
import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SpringAIArticleRecommender implements ArticleRecommender {
    private final VectorStore loader;
    private final TokenTextSplitter splitter;
    private final VectorStoreStatsRepository stats;

    public SpringAIArticleRecommender(
            VectorStore loader,
            TokenTextSplitter splitter,
            VectorStoreStatsRepository stats
    ) {
        this.loader = loader;
        this.splitter = splitter;
        this.stats = stats;
    }

    @Override
    public void ingest(ArticleId id, Collection<ArticleBlock> oldContent, Collection<ArticleBlock> newContent) {
        String jointOldContent = buildContentStringFromDomain(oldContent);
        String jointNewContent = buildContentStringFromDomain(newContent);

        if(jointOldContent.equals(jointNewContent))
            return;

        this.deleteById(id);

        if(jointNewContent.isBlank())
            return;

        List<Document> transformed = this.transform(id, jointNewContent);

        this.loader.add(transformed);
        this.stats.saveChunkCount(id, transformed.size());
    }

    private List<Document> transform(ArticleId id, String content) {
        Document document = Document.builder()
                .text(content)
                .metadata("articleId", id.id())
                .build();

        return this.splitter.split(document);
    }

    private void deleteById(ArticleId id) {
        var filter = new FilterExpressionBuilder()
                .eq("articleId", id.id())
                .build();
        this.loader.delete(filter);
    }

    private @NonNull String buildContentStringFromDomain(Collection<ArticleBlock> blocks) {
        return blocks.stream()
                .filter(b -> b.content().type().isText())
                .map(b -> b.content().content())
                .collect(Collectors.joining("\n"));
    }

    public Collection<ArticleId> recommend(ArticleForRecommendation article, int limit) {
        var averageChunkCount = this.stats.getAverageChunkCount();
        var totalChunkCount = (int) Math.ceil(averageChunkCount * limit);
        var builtContent = buildContentStringFromQuery(article.getContent());
        return this.recommend(builtContent, totalChunkCount)
                .stream()
                .map(document -> document.getMetadata().get("articleId").toString())
                .map(ArticleId::new)
                .filter(id -> !id.equals(article.getId()))
                .collect(Collectors.toSet());
    }

    private @NonNull String buildContentStringFromQuery(Collection<String> content) {
        return content.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining("\n"));
    }

    private List<Document> recommend(String content, int limit) {
        return this.loader.similaritySearch(
                SearchRequest.builder()
                        .query(content)
//                        .similarityThreshold(0.7)
                        .topK(limit)
                        .build()
        );
    }
}
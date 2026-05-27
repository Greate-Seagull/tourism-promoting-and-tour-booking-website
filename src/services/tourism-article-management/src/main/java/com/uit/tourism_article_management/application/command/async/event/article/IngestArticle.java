package com.uit.tourism_article_management.application.command.async.event.article;

import com.uit.tourism_article_management.application.port.article.ArticleRecommender;
import com.uit.tourism_article_management.domain.event.EventHandler;
import com.uit.tourism_article_management.domain.model.article.ArticleContentEdited;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class IngestArticle implements EventHandler<ArticleContentEdited> {
    private final ArticleRecommender recommender;

    public IngestArticle(ArticleRecommender recommender) {
        this.recommender = recommender;
    }

    @Override
    @EventListener
    public void handle(ArticleContentEdited event) {
        event.getOldContent().stream().collect(Collectors.joining());
        this.recommender.ingest(event.getArticleId(), event.getOldContent(), event.getNewContent());
    }
}

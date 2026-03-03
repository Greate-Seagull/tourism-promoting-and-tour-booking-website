package com.uit.tourism_article_management.application.command.sync.article.edit_article_content;

import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.event.DomainEventPublisher;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import org.springframework.stereotype.Service;

@Service
public class EditArticleContentUsecase {
    private final ArticleRepository repo;
    private final DomainEventPublisher publisher;

    public EditArticleContentUsecase(
            ArticleRepository repo,
            DomainEventPublisher publisher
    ) {
        this.repo = repo;
        this.publisher = publisher;
    }

    public Article execute(EditArticleContentCommand command) {
        Article article = this.repo.getById(ArticleId.existing(command.articleId()))
                .orElseThrow(() -> new AggregateNotFound("Article", command.articleId()));

        article.editContent(command.toDomain());

        this.repo.save(article);

        this.publisher.publish(article.getEvents());

        return article;
    }
}

package com.uit.tourism_article_management.application.command.sync.article.edit_article_content;

import com.uit.tourism_article_management.application.exception.ApplicationException;
import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.event.DomainEventPublisher;
import com.uit.tourism_article_management.domain.model.DomainException;
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
        var id = ArticleId.existing(command.articleId());
        var incomingContent = command.toDomain();

        Article article = this.repo.getById(id)
                .orElseThrow(() -> ApplicationException.notfound(id.id()));

        article.editContent(incomingContent);

        this.repo.save(article);

        this.publisher.publish(article.getEvents());

        return article;
    }
}

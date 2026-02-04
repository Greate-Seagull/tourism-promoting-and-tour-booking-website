package com.uit.tourism_article_management.application.command.create_article;

import com.uit.tourism_article_management.application.port.ArticleRepository;
import com.uit.tourism_article_management.domain.model.article.Article;
import org.springframework.stereotype.Service;

@Service
public class CreateArticleUsecase {
    private final ArticleRepository articleRepo;

    public CreateArticleUsecase(final ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    public final void execute(final CreateArticleCommand command) {
        final Article article = Article.create(command.title(), command.introduction(), command
                .coverImageId());

        this.articleRepo.save(article);
    }
}

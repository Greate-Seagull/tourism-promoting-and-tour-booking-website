package com.uit.tourism_article_management.application.command.sync.article.create_article;

import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.model.article.Article;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateArticleUsecase {
    private final ArticleRepository articleRepo;

    public CreateArticleUsecase(final ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Transactional
    public CreateArticleResult execute(final CreateArticleCommand command) {
        final Article article = Article.create(command.title(), command.introduction());
        this.articleRepo.save(article);
        return new CreateArticleResult(article.getId().id(), article.getTitle(), article.getCoverImageId().id());
    }
}

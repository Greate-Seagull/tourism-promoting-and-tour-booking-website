package com.uit.tourism_article_management.application.command.create_article;

import com.uit.tourism_article_management.application.port.ArticleRepository;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import org.springframework.stereotype.Service;

@Service
public class CreateArticleUsecase {
    private final ArticleRepository articleRepo;

    public CreateArticleUsecase(final ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    public CreateArticleResult execute(final CreateArticleCommand command) {
        final ArticleId articleId = this.articleRepo.nextArticleIdentity();
        final MediaId coverImageId = this.articleRepo.nextMediaIdentity();
        final Article article = Article.create(articleId, command.title(), command.introduction(), coverImageId);

        this.articleRepo.save(article);
        return new CreateArticleResult(articleId.toString(), article.getTitle(), article.getCoverImageId().toString());
    }
}

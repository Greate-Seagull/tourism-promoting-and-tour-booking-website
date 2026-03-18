package com.uit.tourism_article_management.application.command.sync.article.change_title;

import com.uit.tourism_article_management.application.exception.ApplicationException;
import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.model.DomainException;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ChangeTitleUsecase {
    private final ArticleRepository articleRepo;

    public ChangeTitleUsecase(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    @Transactional
    public void execute(ChangeTitleCommand command){
        final ArticleId articleId = new ArticleId(command.articleId());
        Article article = this.articleRepo.getById(articleId).orElseThrow(() -> ApplicationException.notfound(articleId.id()));
        article.changeTitle(command.title());
        this.articleRepo.save(article);
    }
}

package com.uit.tourism_article_management.application.command.sync.article.change_introduction;

import com.uit.tourism_article_management.application.port.article.ArticleRepository;
import com.uit.tourism_article_management.domain.exception.AggregateNotFound;
import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import org.springframework.stereotype.Service;

@Service
public class ChangeIntroductionUsecase {
    final private ArticleRepository articleRepo;

    public ChangeIntroductionUsecase(ArticleRepository articleRepo) {
        this.articleRepo = articleRepo;
    }

    public void execute(ChangeIntroductionCommand command){
        final ArticleId articleId = new ArticleId(command.articleId());
        Article article = this.articleRepo.getById(articleId).orElseThrow(() -> new AggregateNotFound("Article", command.articleId()));
        article.changeIntroduction(command.introduction());
        this.articleRepo.save(article);
    }
}

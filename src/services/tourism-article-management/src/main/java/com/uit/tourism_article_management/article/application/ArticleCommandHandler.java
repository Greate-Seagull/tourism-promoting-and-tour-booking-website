package com.uit.tourism_article_management.article.application;

import com.uit.tourism_article_management.account.application.port.AccountRepository;
import com.uit.tourism_article_management.article.application.port.ArticleRepository;
import com.uit.tourism_article_management.article.application.port.MediaStore;
import com.uit.tourism_article_management.article.application.port.TextRefiner;
import com.uit.tourism_article_management.article.domain.Article;
import com.uit.tourism_article_management.article.domain.Editor;
import com.uit.tourism_article_management.article.domain.Report;
import com.uit.tourism_article_management.article.presentation.view.ArticleCreation;
import com.uit.tourism_article_management.article.presentation.view.ArticleEnrichment;
import com.uit.tourism_article_management.article.presentation.view.ArticleModification;
import com.uit.tourism_article_management.article.presentation.view.ReportCreation;
import com.uit.tourism_article_management.exception.ClientException;
import org.jspecify.annotations.NonNull;

public class ArticleCommandHandler {
    private final AccountRepository accountRepository;
    private final MediaStore mediaStore;
    private final ArticleRepository articleRepository;
    private final TextRefiner refiner;

    public ArticleCommandHandler(AccountRepository accountRepository, MediaStore mediaStore, ArticleRepository articleRepository, TextRefiner refiner) {
        this.accountRepository = accountRepository;
        this.mediaStore = mediaStore;
        this.articleRepository = articleRepository;
        this.refiner = refiner;
    }

    public Article create(ArticleCreation creation, String accountId) {
        Editor editor = getEditor(accountId);
        boolean imageExist = this.mediaStore.existsById(creation.coverImageId());
        if (!imageExist)
            throw new ClientException("Article cover image is invalid");
        Article article = Article.create(creation, editor);
        this.articleRepository.save(article);
        return article;
    }

    public void report(String articleId, ReportCreation creation, String accountId) {
        boolean accountExist = this.accountRepository.existsById(accountId);
        if (accountExist)
            throw new ClientException("You are not allowed to perform this operation");
        Article article = this.articleRepository.getById(articleId)
                .orElseThrow(() -> new ClientException("Article does not exist"));
        Report report = article.reportedBy(creation, accountId);
        this.articleRepository.save(report);
    }

    public Article edit(String articleId, ArticleModification modification, String accountId) {
        Editor editor = getEditor(accountId);
        Article article = getArticle(articleId, accountId);
        article.edit(modification, editor);
        this.articleRepository.save(article);
        return article;
    }

    private @NonNull Editor getEditor(String accountId) {
        return this.accountRepository.getEditorById(accountId)
                .orElseThrow(() -> new ClientException("You are not editor"));
    }

    public void enrich(String articleId, ArticleEnrichment enrichment, String accountId) {
        Editor editor = getEditor(accountId);
        Article article = getArticle(articleId, accountId);
        article.enrich(enrichment, this.refiner, editor);
        this.articleRepository.save(article);
    }

    private @NonNull Article getArticle(String articleId, String accountId) {
        return this.articleRepository.getById(articleId, accountId)
                .orElseThrow(() -> new ClientException("You do not own this article"));
    }

    public void remove(String articleId, String accountId) {
        getEditor(accountId);
        this.articleRepository.deleteByIdAndAccountId(articleId, accountId);
    }
}

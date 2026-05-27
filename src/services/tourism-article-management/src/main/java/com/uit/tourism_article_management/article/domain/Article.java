package com.uit.tourism_article_management.article.domain;

import com.uit.tourism_article_management.article.application.port.TextRefiner;
import com.uit.tourism_article_management.article.presentation.view.ArticleCreation;
import com.uit.tourism_article_management.article.presentation.view.ArticleEnrichment;
import com.uit.tourism_article_management.article.presentation.view.ArticleModification;
import com.uit.tourism_article_management.article.presentation.view.ReportCreation;
import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.exception.InternalException;

import java.util.UUID;

public class Article {
    private String id;
    private String title;
    private String introduction;
    private String coverImage;
    private String accountId;
    private String cleanHtml;
    private String bodyText;

    private Article() {}
    public static Article create(ArticleCreation creation, Editor editor) {
        Article article = new Article();
        article.id = UUID.randomUUID().toString();
        article.setOwner(editor);
        article.setTitle(creation.title());
        article.setIntroduction(creation.introduction());
        article.setCoverImage(creation.coverImageId());
        return article;
    }

    private void setOwner(Editor editor) {
        if (editor == null)
            throw new InternalException("editor is null");
        this.accountId = editor.getId();
    }

    private void setCoverImage(String coverImage) {
        if (coverImage == null)
            throw new ClientException("Article cover image must be provided");
        this.coverImage = coverImage;
    }

    private void setIntroduction(String introduction) {
        if (introduction == null || introduction.isBlank())
            throw new ClientException("Article introduction must be provided");
        this.introduction = introduction;
    }

    private void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new ClientException("Article title must be provided");
        this.title = title;
    }

    public Report reportedBy(ReportCreation creation, String accountId) {
        Report.requireReason(creation.reason());
        return new Report(creation.reason(), this.id, accountId);
    }

    public void edit(ArticleModification modification, Editor editor) {
        if (!modification.getTitle().isAbsent())
            this.setTitle(modification.getTitle().data());
        if (!modification.getIntroduction().isAbsent())
            this.setIntroduction(modification.getIntroduction().data());
        if (!modification.getCoverImage().isAbsent())
            this.setCoverImage(modification.getCoverImage().data());
    }

    public void enrich(ArticleEnrichment enrichment, TextRefiner refiner, Editor editor) {
        requireOwnedBy(editor);
        this.setCleanHtml(refiner.sanitize(enrichment.completeHtml()));
        this.setContent(refiner.extractText(enrichment.completeHtml()));
    }

    private void setContent(String raw) {
        if (raw == null || raw.isBlank())
            this.bodyText = null;
        else
            this.bodyText = raw;
    }

    private void setCleanHtml(String sanitize) {
        if (sanitize == null || sanitize.isBlank())
            this.cleanHtml = null;
        else
            this.cleanHtml = sanitize;
    }

    private void requireOwnedBy(Editor editor) {
        if (!this.isOwnedBy(editor.getId()))
            throw new ClientException("You do not own this article");
    }

    private boolean isOwnedBy(String id) {
        return this.accountId.equals(id);
    }

    public void removeBy(Editor editor) {
        requireOwnedBy(editor);
    }
}

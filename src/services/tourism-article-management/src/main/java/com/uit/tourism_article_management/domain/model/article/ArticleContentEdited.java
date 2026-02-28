package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.event.DomainEvent;

import java.util.Collection;

public class ArticleContentEdited extends DomainEvent {
    private ArticleId articleId;
    private Collection<ArticleBlock> oldContent;
    private Collection<ArticleBlock> newContent;

    public ArticleContentEdited(
            ArticleId articleId,
            Collection<ArticleBlock> oldContent,
            Collection<ArticleBlock> newContent
    ) {
        this.articleId = articleId;
        this.oldContent = oldContent;
        this.newContent = newContent;
    }

    public ArticleId getArticleId() {
        return articleId;
    }

    public Collection<ArticleBlock> getOldContent() {
        return oldContent;
    }

    public Collection<ArticleBlock> getNewContent() {
        return newContent;
    }

    @Override
    public String toString() {
        return String.format("ID: %s | Added blocks: %d | Deleted blocks: %d",
                articleId.id(),
                oldContent.size(),
                newContent.size()
        );
    }
}

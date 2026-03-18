package com.uit.tourism_article_management.application.command.sync.article.edit_article_content;

import com.uit.tourism_article_management.domain.model.article.ArticleBlock;

import java.util.Collection;

public record EditArticleContentCommand(
        String articleId,
        Collection<EditBlockCommand> content
) {
    public Collection<ArticleBlock> toDomain() {
        return content.stream().map(EditBlockCommand::toDomain).toList();
    }
}

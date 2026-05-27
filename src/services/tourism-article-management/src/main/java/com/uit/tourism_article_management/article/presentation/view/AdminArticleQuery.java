package com.uit.tourism_article_management.article.presentation.view;

public record AdminArticleQuery(
        String articleId,
        String editorAccountId,
        Integer minReports,
        Integer maxReports
) {

}

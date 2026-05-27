package com.uit.tourism_article_management.article.presentation.view;

public record ArticleQuery(
        String text,

        Integer minView,
        Integer maxView,

        Integer minReadingMinutes,
        Integer maxReadingMinutes
) {
}

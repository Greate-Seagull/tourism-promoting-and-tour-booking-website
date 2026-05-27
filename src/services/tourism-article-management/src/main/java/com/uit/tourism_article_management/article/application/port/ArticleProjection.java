package com.uit.tourism_article_management.article.application.port;

import com.uit.tourism_article_management.article.presentation.view.CompleteArticle;

public interface ArticleProjection {
    CompleteArticle findCompleteById(String articleId);
}

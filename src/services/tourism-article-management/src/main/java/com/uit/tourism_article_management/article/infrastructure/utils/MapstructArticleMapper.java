package com.uit.tourism_article_management.article.infrastructure.utils;

import com.uit.tourism_article_management.article.domain.Article;
import com.uit.tourism_article_management.article.presentation.view.ArticleModifiable;
import com.uit.tourism_article_management.article.presentation.view.CompleteArticle;

public interface MapstructArticleMapper {
    CompleteArticle toComplete(Article article);

    ArticleModifiable toModifiable(Article article);
}

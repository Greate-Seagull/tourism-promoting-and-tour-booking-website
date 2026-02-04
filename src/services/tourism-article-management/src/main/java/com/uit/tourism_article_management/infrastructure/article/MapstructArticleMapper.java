package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.domain.model.article.Article;

//@Mapper(componentModel = "spring")
public interface MapstructArticleMapper {
    JpaArticle toPersistence(Article article);
}

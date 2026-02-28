package com.uit.tourism_article_management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.uit.tourism_article_management.domain.model.article.Article;

import java.util.Collection;
import java.util.Comparator;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "content"})
public record ArticleResponse(
        String id,
        Collection<ArticleBlockResponse> content
) {

    public static ArticleResponse fromDomain(Article result) {
        return new ArticleResponse(
                result.getId().id(),
                result.getContent().stream()
                        .map(ArticleBlockResponse::fromDomain)
                        .sorted(Comparator.comparing(ArticleBlockResponse::order))
                        .toList()
        );
    }
}

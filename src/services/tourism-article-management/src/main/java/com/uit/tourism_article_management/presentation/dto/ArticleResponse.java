package com.uit.tourism_article_management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.uit.tourism_article_management.domain.model.article.Article;

import java.util.Collection;
import java.util.Comparator;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "title", "introduction", "coverImageId", "content"})
public record ArticleResponse(
        String id,
        String title,
        String introduction,
        String coverImageId,
        Collection<ArticleBlockResponse> content
) {

    public static ArticleResponse fromDomain(Article domain) {
        return new ArticleResponse(
                domain.getId().id(),
                domain.getTitle(),
                domain.getIntroduction(),
                domain.getCoverImageId().id(),
                domain.getContent().stream()
                        .map(ArticleBlockResponse::fromDomain)
                        .sorted(Comparator.comparing(ArticleBlockResponse::order))
                        .toList()
        );
    }
}

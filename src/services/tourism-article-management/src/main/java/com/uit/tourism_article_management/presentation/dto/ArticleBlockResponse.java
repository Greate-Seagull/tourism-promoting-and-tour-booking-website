package com.uit.tourism_article_management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "type", "content", "mediaId"})
public record ArticleBlockResponse(
        String id,
        String type,
        String content,
        String mediaId,
        String style
) {
    public static ArticleBlockResponse fromDomain(ArticleBlock articleBlock) {
        return new ArticleBlockResponse(
                articleBlock.id(),
                articleBlock.content().type().name(),
                articleBlock.content().content(),
                articleBlock.content().safeMediaId().map(MediaId::id).orElse(null),
                articleBlock.style()
        );
    }
}

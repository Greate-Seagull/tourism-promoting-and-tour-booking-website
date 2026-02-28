package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

public record EditBlockCommand(String id, String type, String mediaId, String content, String style, int order) {
    public ArticleBlock toDomain() {
        return new ArticleBlock(
                this.id,
                new BlockContent(
                        BlockType.existing(this.type),
                        this.content,
                        Optional.ofNullable(MediaId.existing(this.mediaId))
                ),
                this.style,
                this.order
        );
    }
}

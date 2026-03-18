package com.uit.tourism_article_management.domain.model.article;

public record ArticleBlock(String id, BlockContent content, String style) {
    public static ArticleBlock construct(
            String id,
            String type,
            String content,
            String mediaId,
            String style
    ) {
        var composedContent = BlockContent.constructContent(type, content, mediaId);
        return new ArticleBlock(
                id,
                composedContent,
                style
        );
    }

    public ArticleBlock identify(String id) {
        return new ArticleBlock(
                id,
                this.content,
                this.style
        );
    }
}

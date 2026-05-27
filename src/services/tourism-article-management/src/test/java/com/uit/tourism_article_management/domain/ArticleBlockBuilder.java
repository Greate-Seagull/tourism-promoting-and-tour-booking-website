package com.uit.tourism_article_management.domain;

import com.uit.tourism_article_management.domain.model.article.ArticleBlock;

public class ArticleBlockBuilder {
    private final String id;
    private String type = null;
    private String content = null;
    private String mediaId = null;
    private String style = null;

    public ArticleBlockBuilder(String id) {
        this.id = id;
    }

    public static ArticleBlockBuilder paragraph(String id){
        var builder = new ArticleBlockBuilder(id);
        builder.withType("paragraph");
        return builder;
    }

    public static ArticleBlockBuilder heading(String id){
        var builder = new ArticleBlockBuilder(id);
        builder.withType("heading");
        return builder;
    }

    public static ArticleBlockBuilder video(String id){
        var builder = new ArticleBlockBuilder(id);
        builder.withType("video");
        return builder;
    }

    public static ArticleBlockBuilder image(String id){
        var builder = new ArticleBlockBuilder(id);
        builder.withType("image");
        return builder;
    }

    public ArticleBlockBuilder withType(String type){
        this.type = type;
        return this;
    }

    public ArticleBlockBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public ArticleBlockBuilder withMediaId(String mediaId){
        this.mediaId = mediaId;
        return this;
    }

    public ArticleBlockBuilder withStyle(String style){
        this.style = style;
        return this;
    }

    public ArticleBlock build() {
        return ArticleBlock.construct(
                this.id,
                this.type,
                this.content,
                this.mediaId,
                this.style
        );
    }

    public ArticleBlock buildFixture() {
        return ArticleBlock.construct(
                this.id,
                this.type,
                this.content == null ? "test context" : this.content,
                this.mediaId,
                this.style == null ? "test context" : this.style
        );
    }
}

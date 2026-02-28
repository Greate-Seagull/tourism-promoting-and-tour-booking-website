package com.uit.tourism_article_management.domain;

import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.article.BlockContent;
import com.uit.tourism_article_management.domain.model.article.BlockType;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

public class ArticleBlockBuilder {
    private final String id;
    private String type = null;
    private String content = null;
    private String mediaId = null;
    private String style = null;
    private int order = 1;

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

    public ArticleBlockBuilder withOrder(int order) {
        this.order = order;
        return this;
    }

    public ArticleBlock build() {
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

    public ArticleBlock buildFixture() {
        return new ArticleBlock(
                this.id,
                new BlockContent(
                        BlockType.existing(this.type),
                        this.content == null ? "test context" : this.content,
                        Optional.ofNullable(MediaId.existing(this.mediaId))
                ),
                this.style == null ? "test context" : this.style,
                this.order
        );
    }
}

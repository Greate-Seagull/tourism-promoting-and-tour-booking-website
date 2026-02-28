package com.uit.tourism_article_management.domain;

import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.*;

public class ArticleBuilder {
    private String title = "test context";
    private String introduction = "test context";
    private String coverImageId = "test context";
    private List<ArticleBlock> content = new ArrayList<>();

    public static ArticleBuilder builder() {
        return new ArticleBuilder();
    }

    public ArticleBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public ArticleBuilder withIntroduction(String introduction) {
        this.introduction = introduction;
        return this;
    }

    public ArticleBuilder withContentBlock(ArticleBlock block){
        this.content.add(block);
        return this;
    }

    public ArticleBuilder withContentBlocks(ArticleBlock... blocks){
        this.content.addAll(Arrays.stream(blocks).toList());
        return this;
    }

    public Article build() {
        return Article.rehydrate(
                ArticleId.nextIdentity(),
                this.title,
                this.introduction,
                MediaId.existing(this.coverImageId),
                new HashSet<>(this.content)
        );
    }
}

package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.domain.model.article.Article;
import com.uit.tourism_article_management.domain.model.article.ArticleBlock;
import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class JpaArticle {
    @Id
    private String articleId;
    private String title;
    private String introduction;
    private String coverImageId;

    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Collection<JpaArticleBlock> content;

    public void apply(Article article) {
        this.applyArticleId(article.getId());
        this.setTitle(article.getTitle());
        this.setIntroduction(article.getIntroduction());
        this.applyCoverImage(article.getCoverImageId());
        this.applyContent(article.getContent());
    }

    private void applyArticleId(ArticleId domainArticleId) {
        if(domainArticleId == null)
            return;
        this.setArticleId(domainArticleId.id());
    }

    private void applyCoverImage(MediaId domainImage) {
        if(domainImage == null)
            return;
        this.setCoverImageId(domainImage.id());
    }

    private void applyContent(List<ArticleBlock> domainContent) {
        if(domainContent == null)
            return;

        var content = new ArrayList<JpaArticleBlock>();
        for (int i = 0; i < domainContent.size(); i++) {
            var block = JpaArticleBlock.fromDomain(domainContent.get(i), this);
            block.setBlockOrder(i);
            content.add(block);
        }

        this.setContent(content);
    }

    public static JpaArticle fromDomain(Article article) {
        var persist = new JpaArticle();
        persist.apply(article);
        return persist;
    }

    public static Article toDomain(JpaArticle jpaArticle) {
        if  (jpaArticle == null) return null;

        return new Article(
                new ArticleId(jpaArticle.getArticleId()),
                jpaArticle.getTitle(),
                jpaArticle.getIntroduction(),
                new MediaId(jpaArticle.getCoverImageId()),
                jpaArticle.getContent().stream()
                        .sorted(Comparator.comparing(JpaArticleBlock::getBlockOrder))
                        .map(JpaArticleBlock::toDomain)
                        .toList()
        );
    }
}

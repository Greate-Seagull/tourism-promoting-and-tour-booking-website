package com.uit.tourism_article_management.infrastructure.article;

import com.uit.tourism_article_management.domain.model.article.*;
import com.uit.tourism_article_management.domain.model.media.MediaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Optional;

@Entity
@Table(name = "article_blocks")
@Getter
@Setter
public class JpaArticleBlock {
    @EmbeddedId
    private JpaArticleBlockId id;
    private String type;
    private int blockOrder;
    private String mediaId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String content;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String style;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private JpaArticle article;

    public static JpaArticleBlock fromDomain(ArticleBlock articleBlock, JpaArticle article) {
        var persist = new JpaArticleBlock();
        persist.setStyle(articleBlock.style());

        var blockContent = articleBlock.content();
        persist.setType(blockContent.type().name());
        persist.setContent(blockContent.content());
        persist.setMediaId(blockContent.safeMediaId().map(MediaId::id).orElse(null));

        persist.setArticle(article);
        persist.setId(new JpaArticleBlockId(article.getArticleId(), articleBlock.id()));
        return persist;
    }

    public static ArticleBlock toDomain(JpaArticleBlock jpaArticleBlock) {
        if(jpaArticleBlock == null) return null;
        return new ArticleBlock(
                jpaArticleBlock.getId().articleBlockId(),
                new BlockContent(
                        BlockType.rehydrate(jpaArticleBlock.getType()),
                        jpaArticleBlock.getContent(),
                        Optional.ofNullable(jpaArticleBlock.getMediaId())
                                .map(MediaId::new)
                                .orElse(null)
                ),
                jpaArticleBlock.getStyle()
        );
    }
}

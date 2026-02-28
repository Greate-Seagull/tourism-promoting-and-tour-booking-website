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
    @Id
    private String articleBlockId;
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
    @JoinColumn(name = "article_id")
    private JpaArticle article;

    public static JpaArticleBlock fromDomain(ArticleBlock articleBlock) {
        var persist = new JpaArticleBlock();
        persist.setArticleBlockId(articleBlock.id());
        persist.setStyle(String.valueOf(articleBlock.style()));
        persist.setBlockOrder(articleBlock.order());

        var blockContent = articleBlock.content();
        persist.setType(blockContent.type().name());
        persist.setContent(String.valueOf(blockContent.content()));
        persist.setMediaId(blockContent.mediaId().map(MediaId::id).orElse(null));
        return persist;
    }

    public static ArticleBlock toDomain(JpaArticleBlock jpaArticleBlock) {
        if(jpaArticleBlock == null) return null;
        return new ArticleBlock(
                jpaArticleBlock.getArticleBlockId(),
                new BlockContent(
                        BlockType.existing(jpaArticleBlock.getType()),
                        jpaArticleBlock.getContent(),
                        Optional.ofNullable(MediaId.existing(jpaArticleBlock.getMediaId()))
                ),
                jpaArticleBlock.getStyle(),
                jpaArticleBlock.getBlockOrder()
        );
    }
}

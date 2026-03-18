package com.uit.tourism_article_management.infrastructure.article;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

//@Embeddable
//@Getter
//@Setter
//public class JpaArticleBlockId implements Serializable {
//    private String articleId;
//    private String articleBlockId;
//
//    public JpaArticleBlockId(String articleId, String articleBlockId) {
//        this.articleId = articleId;
//        this.articleBlockId = articleBlockId;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj instanceof JpaArticleBlockId other) {
//            return this.articleId.equals(other.articleId) && this.articleBlockId.equals(other.articleBlockId);
//        };
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(articleId, articleBlockId);
//    }
//}

@Embeddable
public record JpaArticleBlockId(
        @Column(name = "article_id")
        String articleId,
        @Column(name = "article_block_id")
        String articleBlockId
) {
}
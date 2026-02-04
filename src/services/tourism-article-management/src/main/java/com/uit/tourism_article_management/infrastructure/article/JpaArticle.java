package com.uit.tourism_article_management.infrastructure.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class JpaArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String articleId;
    private final String title;
    private final String introduction;
    private final String coverImageId;

    public JpaArticle(String title, String introduction, String coverImageId) {
        this.title = title;
        this.introduction = introduction;
        this.coverImageId = coverImageId;
    }
}

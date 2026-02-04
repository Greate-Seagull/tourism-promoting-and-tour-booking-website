package com.uit.tourism_article_management.infrastructure.article;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class JpaArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String articleId;
    private String title;
    private String introduction;
    private String coverImageId;

    // This is for JPA to build
    public JpaArticle() {};
}

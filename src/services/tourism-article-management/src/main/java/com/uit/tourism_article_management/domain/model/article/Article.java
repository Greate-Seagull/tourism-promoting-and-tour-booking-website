package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.exception.BlankTextException;
import com.uit.tourism_article_management.domain.exception.InvalidSizeException;
import com.uit.tourism_article_management.domain.exception.MissingFieldException;
import com.uit.tourism_article_management.domain.model.media.MediaId;

public class Article {
    private final ArticleId id;
    private String title;
    private String introduction;
    private MediaId coverImageId;

    private Article(ArticleId id) {
        if (id == null)
            throw new MissingFieldException("articleId");

        this.id = id;
    }

    public static Article rehydrate(ArticleId articleId, String title, String introduction, MediaId coverImageId) {
        Article article = new Article(articleId);
        article.title = title;
        article.introduction = introduction;
        article.coverImageId = coverImageId;
        return article;
    }

    public static Article create(ArticleId articleId, String title, String introduction, MediaId coverImageId) {
        Article article = new Article(articleId);
        article.changeTitle(title);
        article.changeIntroduction(introduction);
        article.changeCoverImage(coverImageId);
        return article;
    }

    private void changeCoverImage(MediaId coverImageId) {
        if (coverImageId == null)
            throw new MissingFieldException("coverImageId");

        this.coverImageId = coverImageId;
    }

    public void changeIntroduction(String introduction) {
        if (introduction == null)
            throw new MissingFieldException("introduction");
        if (introduction.isBlank())
            throw new BlankTextException("introduction");
        if (introduction.length() > 300)
            throw new InvalidSizeException("introduction", 1, 300, introduction.length());
        this.introduction = introduction;
    }

    public void changeTitle(String title) {
        if (title == null)
            throw new MissingFieldException("title");
        if (title.isBlank())
            throw new BlankTextException("title");
        if (title.length() > 60)
            throw new InvalidSizeException("title", 1, 60, title.length());
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public ArticleId getId() {
        return id;
    }

    public MediaId getCoverImageId() {
        return coverImageId;
    }
}

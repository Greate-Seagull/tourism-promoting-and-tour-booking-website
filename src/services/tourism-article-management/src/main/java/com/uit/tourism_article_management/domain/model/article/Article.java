package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.exception.BlankTextException;
import com.uit.tourism_article_management.domain.exception.InvalidSizeException;
import com.uit.tourism_article_management.domain.exception.MissingFieldException;

public class Article {
    private final ArticleId id;
    private String title;
    private String introduction;
    private String coverImageId;

    private Article(ArticleId id) {
        this.id = id;
    }

    public static Article create(String title, String introduction, String coverImageId) {
        Article article = new Article(new ArticleId(null));
        article.changeTitle(title);
        article.changeIntroduction(introduction);
        article.changeCoverImage(coverImageId);
        return article;
    }

    private void changeCoverImage(String coverImageId) {
        if (coverImageId == null)
            throw new MissingFieldException("coverImageId");
        if (coverImageId.isBlank())
            throw new BlankTextException("coverImageId");
        this.coverImageId = coverImageId;
    }

    private void changeIntroduction(String introduction) {
        if (introduction == null)
            throw new MissingFieldException("introduction");
        if (introduction.isBlank())
            throw new BlankTextException("introduction");
        if (introduction.length() > 300)
            throw new InvalidSizeException("introduction", 1, 300, introduction.length());
        this.introduction = introduction;
    }

    private void changeTitle(String title) {
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

    public String getCoverImageId() {
        return coverImageId;
    }

    public ArticleId getId() {
        return id;
    }
}

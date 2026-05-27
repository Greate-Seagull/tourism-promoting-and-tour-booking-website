package com.uit.tourism_article_management.article.presentation.view;

import com.uit.tourism_article_management.utils.Maybe;

public class ArticleModification {
    private Maybe<String> title = Maybe.absent();
    private Maybe<String> introduction = Maybe.absent();
    private Maybe<String> coverImage = Maybe.absent();

    public Maybe<String> getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        if (title == null)
            this.title = Maybe.cleared();
        else
            this.title = Maybe.of(title);
    }

    public Maybe<String> getIntroduction() {
        return this.introduction;
    }

    public void setIntroduction(String introduction) {
        if (introduction == null)
            this.introduction = Maybe.cleared();
        else
            this.introduction = Maybe.of(introduction);
    }

    public Maybe<String> getCoverImage() {
        return this.coverImage;
    }

    public void setCoverImage(String coverImage) {
        if (coverImage == null)
            this.coverImage = Maybe.cleared();
        else
            this.coverImage = Maybe.of(coverImage);
    }
}

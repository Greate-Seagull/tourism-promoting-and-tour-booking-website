package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.exception.BlankText;
import com.uit.tourism_article_management.domain.exception.InvalidSequence;
import com.uit.tourism_article_management.domain.exception.InvalidSize;
import com.uit.tourism_article_management.domain.exception.MissingField;
import com.uit.tourism_article_management.domain.model.AggregateRoot;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Article extends AggregateRoot {
    private final ArticleId id;
    private String title;
    private String introduction;
    private MediaId coverImageId;
    private Set<ArticleBlock> content;

    private Article(ArticleId id) {
        this.id = id;
    }

    public static Article rehydrate(
            ArticleId articleId,
            String title,
            String introduction,
            MediaId coverImageId,
            Set<ArticleBlock> content
    ) {
        Article article = new Article(articleId);
        article.title = title;
        article.introduction = introduction;
        article.coverImageId = coverImageId;
        article.content = content;
        return article;
    }

    public static Article create(String title, String introduction) {
        ArticleId articleId = new ArticleId(UUID.randomUUID().toString());
        Article article = new Article(articleId);
        article.changeTitle(title);
        article.changeIntroduction(introduction);
        MediaId coverImageId = new MediaId(UUID.randomUUID().toString());
        article.changeCoverImage(coverImageId);
        return article;
    }

    private void changeCoverImage(MediaId coverImageId) {
        this.coverImageId = coverImageId;
    }

    public void changeIntroduction(String introduction) {
        if (introduction == null)
            throw new MissingField("introduction");
        if (introduction.isBlank())
            throw new BlankText("introduction");
        if (introduction.length() > 350)
            throw new InvalidSize("introduction", 1, 350, introduction.length());
        this.introduction = introduction;
    }

    public void changeTitle(String title) {
        if (title == null)
            throw new MissingField("title");
        if (title.isBlank())
            throw new BlankText("title");
        if (title.length() > 60)
            throw new InvalidSize("title", 1, 60, title.length());
        this.title = title;
    }

    public void editContent(Collection<ArticleBlock> incomingContent) {
        this.ensureContentOrder(incomingContent);
        var oldContent = this.getContent();
        this.content = new HashSet<>(incomingContent);

        this.apply(new ArticleContentEdited(
                this.id,
                oldContent,
                this.getContent()
        ));
    }

    private void ensureContentOrder(Collection<ArticleBlock> incomingContent) {
        Set<Integer> orders = incomingContent.stream()
                .map(ArticleBlock::order)
                .collect(Collectors.toSet());

        for(int i = 1; i <= incomingContent.size(); i++){
            if(!orders.contains(i))
                throw new InvalidSequence("ArticleContent", i);
        }
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

    public Set<ArticleBlock> getContent() {
        return Collections.unmodifiableSet(content);
    }
}

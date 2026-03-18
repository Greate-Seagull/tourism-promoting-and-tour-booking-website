package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.model.AggregateRoot;
import com.uit.tourism_article_management.domain.model.DomainException;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.*;
import java.util.stream.Collectors;

public class Article extends AggregateRoot {
    private final ArticleId id;
    private String title;
    private String introduction;
    private MediaId coverImageId;
    private List<ArticleBlock> content;

    private Article(ArticleId id) {
        this.id = id;
        this.content = new ArrayList<>();
    }

    public Article(
            ArticleId articleId,
            String title,
            String introduction,
            MediaId coverImageId,
            List<ArticleBlock> content
    ) {
        this.id = articleId;
        this.title = title;
        this.introduction = introduction;
        this.coverImageId = coverImageId;
        this.content = content;
    }

    public static Article create(String title, String introduction, String media) {
        Article article = new Article(ArticleId.nextIdentity());
        article.changeTitle(title);
        article.changeIntroduction(introduction);
        article.changeCoverImage(MediaId.existing(media));

        article.apply(new ArticleCreated(article.id, article.coverImageId));
        return article;
    }

    private void changeCoverImage(MediaId coverImageId) {
        this.coverImageId = coverImageId;
    }

    public void changeIntroduction(String introduction) {
        if (introduction == null)
            throw DomainException.missing("introduction");
        if (introduction.isBlank())
            throw DomainException.blank("introduction");
        if (introduction.length() > 500)
            throw DomainException.unfit("introduction", 1, 500, introduction.length());
        this.introduction = introduction;
    }

    public void changeTitle(String title) {
        if (title == null)
            throw DomainException.missing("title");
        if (title.isBlank())
            throw DomainException.blank("title");
        if (title.length() > 60)
            throw DomainException.unfit("title", 1, 60, title.length());
        this.title = title;
    }

    public void editContent(Collection<ArticleBlock> incomingContent) {
        var duplicated = this.detectDuplicated(incomingContent);
        if(!duplicated.isEmpty())
            throw DomainException.duplicated("article block id", duplicated);

        var identified = this.ensureIdentified(incomingContent);

        var oldContent = this.getContent();
        var newContent = new ArrayList<>(identified);

        if (oldContent.equals(newContent))
            return;

        this.content = newContent;

        this.apply(new ArticleContentEdited(
                this.id,
                oldContent,
                newContent
        ));
    }

    private Collection<ArticleBlock> ensureIdentified(Collection<ArticleBlock> incomingContent) {
        var seenIds = incomingContent.stream()
                .map(ArticleBlock::id)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        var identified = new ArrayList<ArticleBlock>(incomingContent.size());
        int id = 1;
        for(var block : incomingContent)
        {
            if(block.id() != null){
                identified.add(block);
                continue;
            }

            while(seenIds.contains(Integer.toString(id)))
                id += 1;

            identified.add(block.identify(Integer.toString(id)));
            id += 1;
        }

        return identified;
    }

    private Collection<String> detectDuplicated(Collection<ArticleBlock> incomingContent) {
        Set<String> seenIds = new HashSet<>();
        List<String> duplicated = new ArrayList<>();
        for (var block : incomingContent)
        {
            if (block.id() == null)
                continue;
            if (!seenIds.add(block.id()))
                duplicated.add(block.id());
        }

        return duplicated;
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

    public List<ArticleBlock> getContent() {
        return Collections.unmodifiableList(content);
    }
}

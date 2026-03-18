package com.uit.tourism_article_management.application.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

//public record ArticleView(String id, Collection<ArticleBlockView> content) {
//}

@Getter
@Setter
public class ArticleView {
    String id;
    String title;
    String coverImageId;
    Collection<ArticleBlockView> content;
}

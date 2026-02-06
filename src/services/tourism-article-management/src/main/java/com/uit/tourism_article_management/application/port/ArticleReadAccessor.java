package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.model.article.ArticleId;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

public interface ArticleReadAccessor {
    Optional<MediaId> getCoverImageIdByArticleId(ArticleId articleId);
}

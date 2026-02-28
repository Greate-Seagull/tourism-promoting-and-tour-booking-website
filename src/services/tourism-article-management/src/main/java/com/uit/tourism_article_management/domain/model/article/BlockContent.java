package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.exception.BlankText;
import com.uit.tourism_article_management.domain.exception.MissingField;
import com.uit.tourism_article_management.domain.exception.UnsupportedType;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

public record BlockContent(BlockType type, String content, Optional<MediaId> mediaId) {
    public BlockContent {
        if(type.isText())
            this.requireContent(content);
        else if(type.isMedia())
            this.requireMediaReference(mediaId);
        else
            throw new UnsupportedType(type.name());
    }

    private void requireMediaReference(Optional<MediaId> mediaId) {
        if(mediaId.isEmpty())
            throw new MissingField("mediaId");
    }

    private void requireContent(String content) {
        if(content == null)
            throw new MissingField("content");
        if(content.isBlank())
            throw new BlankText("content");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BlockContent anotherContent) {
            return this.type.equals(anotherContent.type) &&
                    this.content.equals(anotherContent.content) &&
                    this.mediaId.equals(anotherContent.mediaId);
        }
        return false;
    }
}

package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.model.DomainException;
import com.uit.tourism_article_management.domain.model.Errors;
import com.uit.tourism_article_management.domain.model.Result;
import com.uit.tourism_article_management.domain.model.media.MediaId;

import java.util.Optional;

public record BlockContent(BlockType type, String content, MediaId mediaId) {
    public static BlockContent constructContent(
            String type,
            String content,
            String mediaId
    ){
        var detectedType = BlockType.existing(type);
        if(detectedType.isText())
            return BlockContent.composeText(detectedType, content);
        else if(detectedType.isMedia())
            return BlockContent.attachMedia(detectedType, content, mediaId);
        else
            throw DomainException.unsupported(type);
    }

    private static BlockContent attachMedia(BlockType type, String content, String mediaId) {
        var media = MediaId.existing(mediaId);
        return new BlockContent(type, content, media);
    }

    private static BlockContent composeText(BlockType type, String content) {
        if(content == null)
            throw DomainException.missing("content");
        if(content.isBlank())
            throw DomainException.blank("content");
        return new BlockContent(type, content, null);
    }

    public Optional<MediaId> safeMediaId() {
        return Optional.ofNullable(this.mediaId);
    }

    public Optional<String> safeContent() {
        return Optional.ofNullable(this.content);
    }
}

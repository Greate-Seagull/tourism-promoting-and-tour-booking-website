package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.model.DomainException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BlockType {
    HEADING,
    PARAGRAPH,
    QUOTE,
    IMAGE,
    VIDEO
    ;
    private static final Map<String, BlockType> LOOKUP = Stream.of(BlockType.values())
            .collect(Collectors.toMap(v -> v.name().toLowerCase(), v -> v));

    public static final Collection<BlockType> TEXT_TYPES = List.of(HEADING, PARAGRAPH, QUOTE);
    public static final Collection<BlockType> MEDIA_TYPES = List.of(VIDEO, IMAGE);

    public static BlockType rehydrate(String type) {
        if(type == null) throw new IllegalArgumentException("Type cannot be null");

        var found = LOOKUP.get(type.toLowerCase());
        if (found == null ) throw new IllegalArgumentException("Type not found: " + type);

        return found;
    }

    public static BlockType existing(String type) {
        if(type == null)
            throw DomainException.missing("type");

        var found = LOOKUP.get(type.toLowerCase());
        if (found == null )
            throw DomainException.unsupported(type);

        return found;
    }

    public boolean isText() {
        return TEXT_TYPES.contains(this);
    }

    public boolean isMedia() {
        return MEDIA_TYPES.contains(this);
    }
}

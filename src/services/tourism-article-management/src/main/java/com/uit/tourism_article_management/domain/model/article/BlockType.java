package com.uit.tourism_article_management.domain.model.article;

import com.uit.tourism_article_management.domain.exception.MissingField;
import com.uit.tourism_article_management.domain.exception.UnsupportedType;

public enum BlockType {
    HEADING,
    PARAGRAPH,
    IMAGE,
    VIDEO
    ;
    public static BlockType existing(String type) {
        if(type == null)
            throw new MissingField("type");

        for(BlockType blockType: BlockType.values())
            if(blockType.toString().equalsIgnoreCase(type))
                return blockType;

        throw new UnsupportedType(type);
    }

    public boolean isText() {
        return this.equals(BlockType.HEADING) || this.equals(BlockType.PARAGRAPH);
    }

    public boolean isMedia() {
        return this.equals(BlockType.IMAGE) || this.equals(BlockType.VIDEO);
    }
}

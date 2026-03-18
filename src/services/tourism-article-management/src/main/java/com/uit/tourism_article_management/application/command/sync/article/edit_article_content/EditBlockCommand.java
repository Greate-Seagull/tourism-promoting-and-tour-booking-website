package com.uit.tourism_article_management.application.command.sync.article.edit_article_content;

import com.uit.tourism_article_management.domain.model.DomainException;
import com.uit.tourism_article_management.domain.model.article.ArticleBlock;

public record EditBlockCommand(String id, String type, String mediaId, String content, String style) {
    public ArticleBlock toDomain() {
        try{
            return ArticleBlock.construct(
                    id,
                    type,
                    content,
                    mediaId,
                    style
            );
        } catch(DomainException e) {
            throw e.withIdentity("ArticleBlockId", id);
        }
    }
}

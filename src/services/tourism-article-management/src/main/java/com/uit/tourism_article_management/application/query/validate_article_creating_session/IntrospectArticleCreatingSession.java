package com.uit.tourism_article_management.application.query.validate_article_creating_session;

import com.uit.tourism_article_management.application.exception.SessionInvalid;
import com.uit.tourism_article_management.application.port.TokenService;
import com.uit.tourism_article_management.application.port.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IntrospectArticleCreatingSession {
    private final TokenService tokenService;
    private final TokenStore tokenStore;

    public IntrospectArticleCreatingSession(TokenService tokenService, TokenStore tokenStore) {
        this.tokenService = tokenService;
        this.tokenStore = tokenStore;
    }

    public ArticleCreatingSessionResult execute(String sessionId) {
        boolean isValid = this.tokenStore.exist(sessionId);
        if(!isValid) throw new SessionInvalid(sessionId);
        final Map<String, Object> extractedResult = this.tokenService.introspect(sessionId);
        return new ArticleCreatingSessionResult(
                extractedResult.get("title").toString(),
                extractedResult.get("coverImageId").toString()
        );
    }
}

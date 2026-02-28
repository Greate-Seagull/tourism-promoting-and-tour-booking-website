package com.uit.tourism_article_management.application.command.sync.token.initiate_article_creating_session;

import com.uit.tourism_article_management.application.port.token.TokenService;
import com.uit.tourism_article_management.application.port.token.TokenStore;
import com.uit.tourism_article_management.domain.model.session.TokenTtl;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class InitiateArticleCreatingSession {
    private final TokenService tokenService;
    private final TokenStore tokenStore;

    public InitiateArticleCreatingSession(TokenService tokenService, TokenStore tokenStore) {
        this.tokenService = tokenService;
        this.tokenStore = tokenStore;
    }

    public String execute(String title, String coverImageId) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("title", title);
        payload.put("coverImageId", coverImageId);
        final String token = this.tokenService.generateToken(payload);
        this.tokenStore.initiate(token, TokenTtl.ONE_TIME_TOKEN);
        return token;
    }
}

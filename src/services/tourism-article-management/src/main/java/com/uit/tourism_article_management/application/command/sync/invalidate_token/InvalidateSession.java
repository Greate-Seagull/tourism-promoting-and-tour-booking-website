package com.uit.tourism_article_management.application.command.sync.invalidate_token;

import com.uit.tourism_article_management.application.port.TokenStore;
import org.springframework.stereotype.Service;

@Service
public class InvalidateSession {
    private final TokenStore tokenStore;

    public InvalidateSession(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public void execute(String sessionId) {
        this.tokenStore.invalidate(sessionId);
    }
}

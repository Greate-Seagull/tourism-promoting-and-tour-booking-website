package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.model.session.TokenTtl;

public interface TokenStore {
    void initiate(String token, TokenTtl ttlInSeconds);
    boolean exist(String sessionId);
    void invalidate(String sessionId);
}

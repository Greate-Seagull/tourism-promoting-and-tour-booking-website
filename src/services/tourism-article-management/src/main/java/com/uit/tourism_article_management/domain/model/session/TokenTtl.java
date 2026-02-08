package com.uit.tourism_article_management.domain.model.session;

public enum TokenTtl {
    ONE_TIME_TOKEN(900)
    ;
    private final int ttlInSeconds;

    TokenTtl(int ttlInSeconds) {
        this.ttlInSeconds = ttlInSeconds;
    }

    public int value() {
        return ttlInSeconds;
    }
}

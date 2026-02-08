package com.uit.tourism_article_management.infrastructure.token;

import com.uit.tourism_article_management.application.port.TokenStore;
import com.uit.tourism_article_management.domain.model.session.TokenTtl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisTokenStore implements TokenStore {
    protected static final String keyPrefix = "article:session:";

    private final StringRedisTemplate template;

    public RedisTokenStore(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public void initiate(String token, TokenTtl ttlInSeconds) {
        final String key = RedisTokenStore.keyPrefix + token;
        this.template.opsForValue().set(key, "1", ttlInSeconds.value(), TimeUnit.SECONDS);
    }

    @Override
    public boolean exist(String sessionId) {
        return this.template.opsForValue().get(RedisTokenStore.keyPrefix + sessionId) != null;
    }

    @Override
    public void invalidate(String sessionId) {
        this.template.delete(RedisTokenStore.keyPrefix + sessionId);
    }
}

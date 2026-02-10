package com.uit.tourism_article_management.infrastructure.messaging;

import com.uit.tourism_article_management.domain.event.DomainEvent;
import com.uit.tourism_article_management.domain.event.DomainEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisEventPublisher implements DomainEventPublisher {
    public static final String CHANNEL_PREFIX = "channel:";

    private final RedisTemplate<String, Object> template;

    public RedisEventPublisher(RedisTemplate<String, Object> template) {
        this.template = template;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        events.forEach(this::publish);
    }

    private void publish(DomainEvent event) {
        String channel = CHANNEL_PREFIX + event.getClass().getSimpleName();
        this.template.convertAndSend(channel, event);
    }
}

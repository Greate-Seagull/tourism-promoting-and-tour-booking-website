package com.uit.tourism_article_management.infrastructure.messaging;

import com.uit.tourism_article_management.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisEventSubscriber {
    private final ApplicationEventPublisher springEventPublisher;

    public RedisEventSubscriber(ApplicationEventPublisher springEventPublisher) {
        this.springEventPublisher = springEventPublisher;
    }

    /**
     * This method is called by RedisMessageListenerContainer
     * when a message arrives on subscribed channels
     */
    public void onMessage(DomainEvent event) {
        log.info("Event received: {}", event);
        springEventPublisher.publishEvent(event);
    }
}

package com.uit.tourism_article_management.domain.event;

import java.util.List;

public interface DomainEventPublisher {
    void publish(List<DomainEvent> events);
}

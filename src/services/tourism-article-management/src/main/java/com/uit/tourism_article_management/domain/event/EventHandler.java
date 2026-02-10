package com.uit.tourism_article_management.domain.event;

public interface EventHandler<T extends DomainEvent> {
    void handle(T event);
}

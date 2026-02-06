package com.uit.tourism_article_management.domain.model;

import com.uit.tourism_article_management.domain.event.DomainEvent;

import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> events;

    public void addEvent(DomainEvent event){
        events.add(event);
    }

    public void clearEvents() {
        events.clear();
    }
}

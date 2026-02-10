package com.uit.tourism_article_management.domain.model;

import com.uit.tourism_article_management.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AggregateRoot {
    private final List<DomainEvent> events = new ArrayList<>();

    public void apply(DomainEvent event){
        if (event == null)
            throw new RuntimeException("Domain event must not be null");

        events.add(event);
    }

    public void clearEvents() {
        events.clear();
    }

    public List<DomainEvent> getEvents() {
        return Collections.unmodifiableList(events);
    }
}

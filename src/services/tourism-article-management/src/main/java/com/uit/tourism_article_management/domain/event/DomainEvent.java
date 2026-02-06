package com.uit.tourism_article_management.domain.event;

import java.util.Date;

public abstract class DomainEvent {
    private final Date occuredOn;

    protected DomainEvent() {
        this.occuredOn = new Date();
    }

    public Date getOccuredOn() {
        return occuredOn;
    }
}

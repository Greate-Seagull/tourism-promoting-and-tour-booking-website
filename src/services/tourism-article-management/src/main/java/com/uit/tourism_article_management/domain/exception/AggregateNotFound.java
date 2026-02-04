package com.uit.tourism_article_management.domain.exception;

public class AggregateNotFound extends DomainException {
    public static int code = 4;

    public AggregateNotFound(String aggregate, String id) {
        super(AggregateNotFound.code, String.format("%s not found with id %s", aggregate, id));
    }
}

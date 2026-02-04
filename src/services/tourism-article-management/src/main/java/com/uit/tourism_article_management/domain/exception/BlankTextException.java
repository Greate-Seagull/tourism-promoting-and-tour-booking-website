package com.uit.tourism_article_management.domain.exception;

public class BlankTextException extends DomainException {
    public static final int code = 3;

    public BlankTextException(String field) {
        super(BlankTextException.code, String.format("%s must not be blank", field));
    }
}

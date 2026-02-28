package com.uit.tourism_article_management.domain.exception;

public class NonPossitiveValue extends DomainException
{
    private static final int code = 7;

    public NonPossitiveValue(final String field, int value) {
        super(NonPossitiveValue.code, String.format("%s must be positive, actual %d", field, value));
    }
}

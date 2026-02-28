package com.uit.tourism_article_management.domain.exception;

public class InsufficientResource extends DomainException {
    public static final int code = 8;

    public InsufficientResource(String resourceName, int atLeast, int actual) {
        super(code, String.format("%s must be at least %d, actual %d", resourceName, atLeast, actual));
    }
}

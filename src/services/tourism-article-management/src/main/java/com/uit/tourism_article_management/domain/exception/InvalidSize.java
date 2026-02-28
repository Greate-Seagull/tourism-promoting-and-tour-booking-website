package com.uit.tourism_article_management.domain.exception;

public class InvalidSize extends DomainException {
    public static final int code = 1;
    public InvalidSize(String field, int min, int max, int actual) {
        super(InvalidSize.code, String.format("%s must be between %d and %d, actual %d", field, min, max, actual));
    }
}

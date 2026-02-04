package com.uit.tourism_article_management.domain.exception;

public class InvalidSizeException extends DomainException {
    public static final int code = 1;
    public InvalidSizeException(String field, int min, int max, int actual) {
        super(InvalidSizeException.code, String.format("%s must be between %d and %d, actual %d", field, min, max, actual));
    }
}

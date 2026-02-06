package com.uit.tourism_article_management.domain.exception;

public class UnsupportedMimeTypeException extends DomainException {
    public static int code = 5;

    public UnsupportedMimeTypeException(String actualType) {
        super(UnsupportedMimeTypeException.code, String.format("%s is not supported", actualType));
    }
}

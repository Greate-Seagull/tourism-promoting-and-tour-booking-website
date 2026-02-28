package com.uit.tourism_article_management.domain.exception;

public class UnsupportedType extends DomainException {
    public static int code = 5;

    public UnsupportedType(String actualType) {
        super(UnsupportedType.code, String.format("%s is not supported", actualType));
    }
}

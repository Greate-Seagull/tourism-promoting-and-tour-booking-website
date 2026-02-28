package com.uit.tourism_article_management.domain.exception;

public class BlankText extends DomainException {
    public static final int code = 3;

    public BlankText(String field) {
        super(BlankText.code, String.format("%s must not be blank", field));
    }
}

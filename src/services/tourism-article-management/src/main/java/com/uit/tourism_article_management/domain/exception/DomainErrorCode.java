package com.uit.tourism_article_management.domain.exception;

public enum DomainErrorCode {
    UNCATEGORIZE(9999, "Uncategorized error"),
    MISSING_VALUE(9998, "Missing value" ),
    INVALID_TITLE_LENGTH(1, "The title length must be between 1 and 60 characters"),
    INVALID_INTRODUCTION_LENGTH(2, "The introduction length must be between 1 and 300 characters")
    ;
    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    DomainErrorCode(int code, final String message){
        this.code = code;
        this.message = message;
    }
}

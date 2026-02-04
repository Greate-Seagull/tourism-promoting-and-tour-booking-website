package com.uit.tourism_article_management.domain.exception;

public class MissingFieldException extends DomainException {
    public static final int code = 2;
    public MissingFieldException(String field){
        super(MissingFieldException.code, String.format("%s is missing", field));
    }
}

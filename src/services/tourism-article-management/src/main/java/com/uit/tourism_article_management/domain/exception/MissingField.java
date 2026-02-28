package com.uit.tourism_article_management.domain.exception;

public class MissingField extends DomainException {
    public static final int code = 2;
    public MissingField(String field){
        super(MissingField.code, String.format("%s is missing", field));
    }
}

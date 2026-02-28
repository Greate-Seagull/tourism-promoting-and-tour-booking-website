package com.uit.tourism_article_management.domain.exception;

public class InvalidSequence extends DomainException {
    public static final int code = 6;
    public InvalidSequence(String sequence, int index) {
        super(InvalidSequence.code, String.format("%s is missing order %d", sequence, index));
    }
}

package com.uit.tourism_article_management.domain.exception;

//public class DomainException extends RuntimeException {
//    private final DomainErrorCode errorCode;
//
//    public DomainException(final DomainErrorCode errorCode){
//        this.errorCode = errorCode;
//    }
//
//    public DomainErrorCode getErrorCode() {
//        return errorCode;
//    }
//}

public class DomainException extends RuntimeException {
    private final int code;

    protected DomainException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
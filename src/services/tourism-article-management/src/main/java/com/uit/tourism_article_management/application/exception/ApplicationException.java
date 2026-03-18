package com.uit.tourism_article_management.application.exception;

import com.uit.tourism_article_management.domain.model.DomainError;
import com.uit.tourism_article_management.domain.model.Errors;

public class ApplicationException extends RuntimeException {
    private final String code;

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ApplicationException(DomainError error) {
        super(error.message());
        this.code = error.code();
    }

    public static ApplicationException notfound(Object id) {
        return new ApplicationException(Errors.notfound(id.toString()));
    }

    public String getCode() {
        return code;
    }
}

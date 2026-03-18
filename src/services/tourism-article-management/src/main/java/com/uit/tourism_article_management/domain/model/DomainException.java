package com.uit.tourism_article_management.domain.model;

import java.util.Collection;
import java.util.List;

public class DomainException extends RuntimeException {
    private final String code;

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DomainException(DomainError error) {
        super(error.message());
        this.code = error.code();
    }

    public String getCode() {
        return code;
    }

    public static DomainException notfound(String id) {
        return new DomainException(Errors.notfound(id));
    }

    public static DomainException missing(String field) {
        return new DomainException(Errors.missing(field));
    }

    public static DomainException unsupported(String type) {
        return new DomainException(Errors.unsupported(type));
    }

    public static DomainException blank(String field) {
        return new DomainException(Errors.blank(field));
    }

    public static DomainException unfit(String field, int min, int max, int actual) {
        return new DomainException(Errors.unfit(field, min, max, actual));
    }

    public static DomainException discontinuous(String sequence, int order) {
        return new DomainException(Errors.discontinuous(sequence, order));
    }

    public static DomainException insufficient(String referenceCount, int value, int count) {
        return new DomainException(Errors.insufficient("reference count", -value, count));
    }

    public static DomainException duplicated(String field, Collection<String> duplicated) {
        return new DomainException(Errors.duplicated(field, duplicated));
    }

    public DomainException withIdentity(String entity, String id) {
        var enrichedMessage = String.format(
                "[%s=%s] %s",
                entity,
                id,
                this.getMessage()
        );
        return new DomainException(this.code, enrichedMessage);
    }
}
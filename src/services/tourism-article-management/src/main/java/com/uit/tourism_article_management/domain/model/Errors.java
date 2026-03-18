package com.uit.tourism_article_management.domain.model;

import java.util.Collection;
import java.util.List;

public class Errors {
    public static DomainError unsupported(String actualType) {
        return new DomainError("Unsupported", String.format("%s is not supported", actualType));
    }

    public static DomainError missing(String field) {
        return new DomainError("Missing", String.format("%s is missing", field));
    }

    public static DomainError blank(String field) {
        return new DomainError("Blank", String.format("%s must not be blank", field));
    }

    public static DomainError unfit(String field, int min, int max, int actual) {
        return new DomainError("Unfit", String.format("%s must be between %d and %d, actual %d", field, min, max, actual));
    }

    public static DomainError notfound(String id) {
        return new DomainError("Not found", String.format("%s does not exist", id));
    }

    public static DomainError insufficient(String field, int atLeast, int actual) {
        return new DomainError("Insufficient", String.format("%s must be at least %d, actual %d", field, atLeast, actual));
    }

    public static DomainError discontinuous(String sequence, int missing) {
        return new DomainError("Inorder", String.format("%s is missing order %d", sequence, missing));
    }

    public static DomainError duplicated(String field, Collection<String> duplicated) {
        return new DomainError("Duplicated", String.format("%s contains duplicate values of %s", field, duplicated));
    }
}

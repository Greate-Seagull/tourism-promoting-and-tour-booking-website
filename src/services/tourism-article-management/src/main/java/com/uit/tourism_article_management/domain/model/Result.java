package com.uit.tourism_article_management.domain.model;

import java.util.Optional;

public record Result<T> (T value, DomainError error) {
    public static <T> Result<T> success(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> failure(DomainError error) {
        return new Result<>(null, error);
    }

    public boolean isSuccess() {
        return value != null;
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<DomainError> getError() {
        return Optional.ofNullable(error);
    }
}

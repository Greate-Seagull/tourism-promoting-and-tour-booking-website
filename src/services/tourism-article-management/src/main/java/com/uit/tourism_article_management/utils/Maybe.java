package com.uit.tourism_article_management.utils;

public record Maybe<T>(
        T data,
        State state
) {
    public static <T> Maybe<T> absent() {
        return new Maybe<>(null, State.ABSENT);
    }

    public static <T> Maybe<T> cleared() {
        return new Maybe<>(null, State.CLEARED);
    }

    public static <T> Maybe<T> of(T data) {
        return new Maybe<>(data, State.PRESENT);
    }

    public boolean isAbsent() {
        return state == State.ABSENT;
    }

    public enum State {
        ABSENT,
        CLEARED,
        PRESENT
    }
}

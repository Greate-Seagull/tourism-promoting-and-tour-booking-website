package com.uit.tourism_article_management.application.exception;

public class SessionInvalid extends ApplicationException {
    public SessionInvalid(String sessionId) {
        super("Session invalid", String.format("Session is invalid with id %s", sessionId));
    }
}

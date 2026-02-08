package com.uit.tourism_article_management.application.exception;

public class SessionInvalid extends ApplicationException {
    protected static final int code = 10000;

    public SessionInvalid(String sessionId) {
        super(SessionInvalid.code, String.format("Session is invalid with id %s", sessionId));
    }
}

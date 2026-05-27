package com.uit.tourism_article_management.exception;

import java.util.List;
import java.util.stream.Collectors;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(List<String> messages) {
        super(messages.stream()
                .map(message -> "- " + message)
                .collect(Collectors.joining("\n"))
        );
    }
}

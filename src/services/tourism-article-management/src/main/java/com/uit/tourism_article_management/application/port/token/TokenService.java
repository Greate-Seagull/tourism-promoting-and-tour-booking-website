package com.uit.tourism_article_management.application.port.token;

import java.util.Map;

public interface TokenService {
    String generateToken(Map<String, Object> payload);
    Map<String, Object> introspect(String sessionId);
}

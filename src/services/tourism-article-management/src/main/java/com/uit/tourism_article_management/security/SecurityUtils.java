package com.uit.tourism_article_management.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class SecurityUtils {
    public static String getRequiredAccountId() {
        return getAccountId()
                .orElseThrow(() -> new IllegalArgumentException("User muse be signed in"));
    }

    public static Optional<String> getAccountId() {
        var securedAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (securedAuthentication != null &&
                securedAuthentication.getPrincipal() instanceof Jwt claims) {
            return Optional.ofNullable(claims.getClaimAsString("accountId"));
        }
        return Optional.empty();
    }
}

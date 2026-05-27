package com.uit.tourism_article_management.account.presentation.view;

import com.uit.tourism_article_management.account.domain.Role;

import java.util.Set;

public record AccountRoleModification(
        Set<Role> roles,
        String reason
) {
}

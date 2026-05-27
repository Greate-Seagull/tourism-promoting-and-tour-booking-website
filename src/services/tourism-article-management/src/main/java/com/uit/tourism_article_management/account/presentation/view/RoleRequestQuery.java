package com.uit.tourism_article_management.account.presentation.view;

import com.uit.tourism_article_management.account.domain.Role;
import com.uit.tourism_article_management.account.domain.RoleRequestStatus;

public record RoleRequestQuery(
        long id,
        String accountId,
        Role role,
        RoleRequestStatus status
) {
}

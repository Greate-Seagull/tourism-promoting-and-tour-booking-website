package com.uit.tourism_article_management.account.presentation.view;

import com.uit.tourism_article_management.account.domain.Profile;
import com.uit.tourism_article_management.account.domain.Role;

import java.util.List;

public record RoleRequestCreation(
        Role role,
        List<Profile> profiles
) {
}

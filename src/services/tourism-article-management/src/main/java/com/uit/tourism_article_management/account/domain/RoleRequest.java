package com.uit.tourism_article_management.account.domain;

import com.uit.tourism_article_management.exception.ClientException;

import java.util.List;

public record RoleRequest(
        Role role,
        List<Profile> profiles
) {
    public RoleRequest {
        requireProvidingProfiles(profiles);
    }

    static private void requireProvidingProfiles(List<Profile> profiles) {
        if (profiles == null || profiles.isEmpty())
            throw new ClientException("Role request profile must be provided");
    }
}

package com.uit.tourism_article_management.account.domain;

import com.uit.tourism_article_management.account.application.port.Admin;
import com.uit.tourism_article_management.account.presentation.view.Reason;
import com.uit.tourism_article_management.exception.ClientException;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class RoleRequest {
    private String issuerId;
    private Role role;
    private List<Profile> profiles;
    private String reason;
    private String adminId;
    private RoleRequestStatus status;

    public RoleRequest() {
        requireProvidingProfiles(profiles);
    }

    static private void requireProvidingProfiles(List<Profile> profiles) {
        if (profiles == null || profiles.isEmpty())
            throw new ClientException("Role request profile must be provided");
    }

    public void rejectedBy(Reason message, Admin admin) {
        requirePending();
        this.setReason(message.reason());
        this.handledBy(admin);
        this.status = RoleRequestStatus.REJECTED;
    }

    private void handledBy(@NonNull Admin admin) {
        this.adminId = admin.getId();
    }

    private void setReason(String reason) {
        if (reason == null || reason.isBlank())
            throw new ClientException("Reason must be provided");
        this.reason = reason;
    }

    public void approvedBy(Reason message, Admin admin) {
        requirePending();
        this.setReason(message.reason());
        this.handledBy(admin);
        this.status = RoleRequestStatus.APPROVED;
    }

    private void requirePending() {
        if (!this.status.equals(RoleRequestStatus.PENDING))
            throw new ClientException("Role request has been handled");
    }

    public String getIssuer() {
        return this.issuerId;
    }

    public Role getRole() {
        return this.role;
    }
}

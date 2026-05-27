package com.uit.tourism_article_management.account.domain;

import com.uit.tourism_article_management.account.presentation.view.AccountCreation;
import com.uit.tourism_article_management.account.presentation.view.RoleRequestCreation;
import com.uit.tourism_article_management.exception.ClientException;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Account {
    private String id;
    private String fullname;
    private Email email;
    private PhoneNumber phoneNumber;
    private String password;
    private final Set<Role> roles = new HashSet<>();
    private AccountStatus status = AccountStatus.NORMAL;

    private Account() {
    }

    public static Account create(AccountCreation creation, String hashed) {
        Account account = new Account();
        account.id = UUID.randomUUID().toString();
        account.setFullname(creation.fullname());
        account.setEmail(creation.email());
        account.setPhoneNumber(creation.phoneNumber());
        account.setPassword(hashed);
        return account;
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String hashed) {
        if (hashed == null)
            throw new ClientException("Account password must be provided");
        this.password = hashed;
    }

    public RoleRequest apply(RoleRequestCreation creation) {
        requireNotOwnedRole(creation.role());
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setIssuer(this.id);
        roleRequest.setQualifier(creation.role(), creation.profiles());
        roleRequest.setPending();
        return roleRequest;
    }

    private void requireNotOwnedRole(Role roles) {
        if (this.hasRole(roles))
            throw new ClientException("Your account already has this role");
    }

    private boolean hasRole(Role role) {
        return this.roles.contains(role);
    }

    public void grant(Role role) {
        this.roles.add(role);
    }

    public void banned() {
        this.status = AccountStatus.BANNED;
    }

    public String getId() {
        return this.id;
    }

    public void removeRoles(Set<Role> roles) {
        this.roles.removeAll(roles);
    }

    public void grantRoles(Set<Role> roles) {
        this.roles.addAll(roles);
    }

    public String getFullname() {
        return this.fullname;
    }

    private void setFullname(String fullname) {
        if (fullname == null || fullname.isBlank())
            throw new ClientException("Account fullname must be provided");
        this.fullname = fullname;
    }

    public Email getEmail() {
        return this.email;
    }

    private void setEmail(Email email) {
        if (email == null)
            throw new ClientException("Account email must be provided");
        this.email = email;
    }

    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

    private void setPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null)
            throw new ClientException("Account phone number must be provided");
        this.phoneNumber = phoneNumber;
    }
}

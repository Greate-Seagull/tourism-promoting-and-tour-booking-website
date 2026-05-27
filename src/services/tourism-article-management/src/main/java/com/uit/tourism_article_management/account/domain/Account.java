package com.uit.tourism_article_management.account.domain;

import com.uit.tourism_article_management.account.presentation.view.AccountCreation;
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
    private Set<Role> roles = new HashSet<>();

    private Account() {}
    public static Account create(AccountCreation creation, String hashed) {
        Account account = new Account();
        account.id = UUID.randomUUID().toString();
        account.setFullname(creation.fullname());
        account.setEmail(creation.email());
        account.setPhoneNumber(creation.phoneNumber());
        account.setPassword(hashed);
        return account;
    }

    private void setPassword(String hashed) {
        if (hashed == null)
            throw new ClientException("Account password must be provided");
        this.password = hashed;
    }

    private void setPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumber == null)
            throw new ClientException("Account phone number must be provided");
        this.phoneNumber = phoneNumber;
    }

    private void setEmail(Email email) {
        if (email == null)
            throw new ClientException("Account email must be provided");
        this.email = email;
    }

    private void setFullname(String fullname) {
        if (fullname == null || fullname.isBlank())
            throw new ClientException("Account fullname must be provided");
        this.fullname = fullname;
    }

    public String getPassword() {
        return this.password;
    }

    public RoleRequest apply(RoleRequest creation) {
        requireNotOwnedRole(creation.role());
        return creation;
    }

    private void requireNotOwnedRole(Role roles) {
        if (this.hasRole(roles))
            throw new ClientException("Your account already has this role");
    }

    private boolean hasRole(Role role) {
        return this.roles.contains(role);
    }
}

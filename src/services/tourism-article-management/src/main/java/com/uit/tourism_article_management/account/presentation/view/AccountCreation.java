package com.uit.tourism_article_management.account.presentation.view;

import com.uit.tourism_article_management.account.domain.Email;
import com.uit.tourism_article_management.account.domain.PhoneNumber;

public record AccountCreation(
        String fullname,
        PhoneNumber phoneNumber,
        Email email,
        String password
) {
}

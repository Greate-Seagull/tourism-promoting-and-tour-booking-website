package com.uit.tourism_article_management.order.domain;

import com.uit.tourism_article_management.account.domain.Email;
import com.uit.tourism_article_management.account.domain.PhoneNumber;

public record Representative(
        String fullname,
        PhoneNumber phoneNumber,
        Email email
) {
}

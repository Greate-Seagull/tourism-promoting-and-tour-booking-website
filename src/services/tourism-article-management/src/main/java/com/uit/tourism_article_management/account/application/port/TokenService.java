package com.uit.tourism_article_management.account.application.port;

import com.uit.tourism_article_management.account.domain.Account;

public interface TokenService {
    String generate(Account account);
}

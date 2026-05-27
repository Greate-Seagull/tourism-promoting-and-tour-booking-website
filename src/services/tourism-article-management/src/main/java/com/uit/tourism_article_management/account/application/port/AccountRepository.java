package com.uit.tourism_article_management.account.application.port;

import com.uit.tourism_article_management.account.domain.Email;
import com.uit.tourism_article_management.account.domain.PhoneNumber;
import com.uit.tourism_article_management.account.domain.RoleRequest;
import com.uit.tourism_article_management.account.domain.Account;
import com.uit.tourism_article_management.article.domain.Editor;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;

import java.lang.ScopedValue;
import java.util.Optional;

public interface AccountRepository {
    Optional<TourOperator> getTourOperatorById(String accountId);

    boolean existsById(String accountId);

    Optional<Editor> getEditorById(String accountId);

    boolean existsByPhoneOrEmail(PhoneNumber phoneNumber, Email email);

    void save(Account account);

    Optional<Account> getByPhoneNumber(String phoneNumber);

    Optional<Account> getByEmail(String email);

    Optional<Account> getById(String accountId);

    void save(RoleRequest created);

    void save(TourOperator bankWallet);

    Optional<Admin> getAdminById(String accountId);

    Optional<RoleRequest> getRoleRequestById(Long requestId);
}

package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.tour.AccountId;
import com.uit.tourism_article_management.domain.tour.TourOperator;

public interface AccountRepository {
    TourOperator getTourOperatorById(AccountId id);

    boolean existsById(String accountId);
}

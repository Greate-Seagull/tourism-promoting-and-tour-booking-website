package com.uit.tourism_article_management.account;

import com.uit.tourism_article_management.article.domain.Editor;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;

import java.util.Optional;

public interface AccountRepository {
    Optional<TourOperator> getTourOperatorById(String accountId);

    boolean existsById(String accountId);

    Optional<Editor> getEditorById(String accountId);
}

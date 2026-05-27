package com.uit.tourism_article_management.tour.domain;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;

public class TourRecord {
    private String accountId;
    private boolean isPublished;

    public boolean isPublished() {
        return this.isPublished;
    }

    protected boolean isOwnedBy(TourOperator tourOperator) {
        return this.accountId.equals(tourOperator.getId());
    }

    protected boolean isOwnedBy(String id) {
        return this.accountId.equals(id);
    }

    protected void requireOwnedBy(TourOperator tourOperator) {
        if (!isOwnedBy(tourOperator))
            throw new ClientException("You do not own this tour");
    }

    protected void requireNotOwnedBy(String accountId) {
        if (this.isOwnedBy(accountId))
            throw new ClientException("Tour must not belong to you");
    }

    public PriceTable composePrice(PriceTable creation, TourOperator tourOperator) {
        requireOwnedBy(tourOperator);
        return creation;
    }

    protected void requirePublished() {
        if (!this.isPublished())
            throw new ClientException("Tour must be published");
    }

    protected void publish() {
        this.isPublished = true;
    }

    protected void unpublish() {
        this.isPublished = false;
    }

    protected void requireNotPublished() {
        if (this.isPublished())
            throw new ClientException("Tour must not be published");
    }
}

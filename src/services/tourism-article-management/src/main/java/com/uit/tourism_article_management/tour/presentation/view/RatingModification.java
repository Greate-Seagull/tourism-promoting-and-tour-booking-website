package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.utils.Maybe;

import java.util.Objects;

public class RatingModification {
    private Maybe<Integer> score = Maybe.absent();
    private Maybe<String> review = Maybe.absent();

    public Maybe<Integer> getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = Maybe.of(Objects.requireNonNullElse(score, 0));
    }

    public Maybe<String> getReview() {
        return this.review;
    }

    public void setReview(String review) {
        if (review == null)
            this.review = Maybe.cleared();
        else
            this.review = Maybe.of(review);
    }
}

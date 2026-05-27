package com.uit.tourism_article_management.tour.domain.rating;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.tour.presentation.view.RatingCreation;
import com.uit.tourism_article_management.tour.presentation.view.RatingModification;

public record Rating(
        int score,
        String review,
        String accountId,
        String tourId
) {
    public static Rating rate(RatingCreation creation, String tourId, String accountId) {
        requireValidScore(creation.score());
        return new Rating(
                creation.score(),
                creation.review(),
                accountId,
                tourId
        );
    }

    private static void requireValidScore(int score) {
        if (score < 0 || score > 5)
            throw new ClientException("Rating score must be between 0 and 5");
    }

    public Rating adjust(RatingModification modification) {
        var score = this.score;
        var review = this.review;
        if (!modification.getScore().isAbsent())
            score = setScore(modification.getScore().data());
        if (!modification.getReview().isAbsent())
            review = setReview(modification.getReview().data());
        return new Rating(score, review, accountId, tourId);
    }

    private String setReview(String review) {
        if (review == null || review.isBlank())
            return null;
        return review;
    }

    private int setScore(Integer score) {
        if (score == null)
            return 0;
        requireValidScore(score);
        return score;
    }
}

package com.uit.tourism_article_management.tour.application.port;

import com.uit.tourism_article_management.tour.domain.rating.Rating;

import java.util.Optional;

public interface RatingRepository {
    void save(Rating rating);

    Optional<Rating> getByTourIdAdnAccountId(String tourId, String accountId);
}

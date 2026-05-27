package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.domain.tour.TourStatus;

public record OperatorTourQuery(
        String id,
        String title,
        TourStatus status,

        Integer minRegistrations,
        Integer maxRegistrations
) {
}

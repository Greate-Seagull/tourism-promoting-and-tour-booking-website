package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.tour.domain.departure.Transit;

import java.time.ZonedDateTime;

public record ProductQuery(
        String id,
        String text,

        Integer dayCount,
        Integer nightCount,

        Integer minPrice,
        Integer maxPrice,

        String pickUp,
        String dropOff,

        Transit transit,

        ZonedDateTime fromDate
) {
}

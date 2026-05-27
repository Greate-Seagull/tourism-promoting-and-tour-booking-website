package com.uit.tourism_article_management.presentation.controller.tour;

import com.uit.tourism_article_management.domain.tour.Meal;

import java.util.List;

public record TimelineItem(
        List<String> destinations,
        List<Meal> meals,
        String details
) {
    public TimelineItem
    {
        if (destinations == null || destinations.isEmpty())
            throw new RuntimeException("Destinations in timeline must be provided");
        if (meals == null || meals.isEmpty())
            throw new RuntimeException("Meals in timeline must be provided");
    }
}

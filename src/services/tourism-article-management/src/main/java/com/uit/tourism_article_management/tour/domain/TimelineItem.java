package com.uit.tourism_article_management.tour.domain;

import com.uit.tourism_article_management.exception.ClientException;

import java.util.*;

public record TimelineItem(
        List<Meal> meals,
        List<String> destinations,
        String details
) {
    public TimelineItem {
        requireNonDuplicatedMeals(meals);
        requireMinimumDestinations(destinations);
        requireNonBlankDetails(details);
    }

    static private void requireNonBlankDetails(String details) {
        if(details == null || details.isBlank())
            throw new ClientException("Timeline details must not be blank");
    }

    static private void requireMinimumDestinations(List<String> destinations) {
        if (destinations == null || destinations.isEmpty())
            throw new ClientException("Timeline destinations must be provided at least 2");

        List<String> errors = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (String destination : destinations) {
            if (!seen.add(destination))
                errors.add(String.format("Destination %s is duplicated", destination));
        }
        if (!errors.isEmpty())
            throw new ClientException(errors);
    }

    static private void requireNonDuplicatedMeals(List<Meal> meals) {
        if (meals == null || meals.isEmpty())
            throw new ClientException("Timeline meals must be provided at least 1");

        List<String> errors = new ArrayList<>();
        EnumSet<Meal> seen = EnumSet.noneOf(Meal.class);
        for (Meal meal : meals) {
            if (!seen.add(meal))
                errors.add(String.format("Meal %s is duplicated", meal));
        }
        if (!errors.isEmpty())
            throw new ClientException(errors);
    }
}

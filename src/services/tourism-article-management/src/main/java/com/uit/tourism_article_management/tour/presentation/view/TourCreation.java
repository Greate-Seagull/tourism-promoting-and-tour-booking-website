package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.tour.domain.TourPolicy;
import com.uit.tourism_article_management.tour.domain.TimelineItem;

import java.util.List;
import java.util.Set;

public record TourCreation(
        String title,
        Set<String> images,
        String pickUp,
        String dropOff,
        List<TimelineItem> timeline,
        TourPolicy policy
) {
}

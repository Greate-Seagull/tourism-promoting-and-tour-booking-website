package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.tour.domain.Description;
import com.uit.tourism_article_management.tour.domain.Destination;
import com.uit.tourism_article_management.tour.domain.TimelineItem;
import com.uit.tourism_article_management.tour.domain.TourPolicy;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record CompleteTour(
        String id,
        String title,
        Description description,
        Set<String> images,
        Destination pickUp,
        Destination dropOff,
        List<TimelineItem> timeline,
        TourPolicy policy,
        Set<LocalDate> schedule
) {
}

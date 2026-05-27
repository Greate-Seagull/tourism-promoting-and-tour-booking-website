package com.uit.tourism_article_management.presentation.controller.tour;

import java.util.List;

public record TourCreation(
        String title,
        String introduction,
        List<String> images,
        String pickup,
        String dropoff,
        Policy policy,
        List<TimelineItem> timeline
) {
}

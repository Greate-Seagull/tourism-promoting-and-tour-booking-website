package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.presentation.controller.tour.Policy;
import com.uit.tourism_article_management.tour.domain.Description;
import com.uit.tourism_article_management.tour.domain.TimelineItem;

import java.util.List;
import java.util.Set;

public record TourModifiable(
        Description description,
        Set<String> images,
        Policy policy,
        List<TimelineItem> timeline
) {
}

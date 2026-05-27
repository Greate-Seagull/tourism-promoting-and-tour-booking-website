package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.tour.HostedTour;

public interface TourRepository {
    void save(HostedTour tour);
}

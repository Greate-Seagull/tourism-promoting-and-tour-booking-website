package com.uit.tourism_article_management.application.port;

import com.uit.tourism_article_management.domain.tour.Destination;

public interface DestinationRepository {
    Destination getById(String pickup);
}

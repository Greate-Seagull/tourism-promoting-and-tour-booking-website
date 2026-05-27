package com.uit.tourism_article_management.tour.application.port;

import com.uit.tourism_article_management.tour.domain.Destination;

import java.util.Optional;

public interface DestinationRepository {
    Optional<Destination> getByName(String lowerCase);
}

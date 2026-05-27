package com.uit.tourism_article_management.tour.infrastructure.utils;

import com.uit.tourism_article_management.tour.domain.rating.Rating;
import com.uit.tourism_article_management.tour.domain.Tour;
import com.uit.tourism_article_management.tour.presentation.view.RatingModifiable;
import com.uit.tourism_article_management.tour.presentation.view.TourCreation;
import com.uit.tourism_article_management.tour.presentation.view.TourModifiable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapstructTourMapper {
    TourCreation toCreation(Tour tour);

    TourModifiable toModifiable(Tour tour);

    RatingModifiable toModifiable(Rating rating);
}

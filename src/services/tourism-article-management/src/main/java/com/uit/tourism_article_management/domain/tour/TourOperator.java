package com.uit.tourism_article_management.domain.tour;

import com.uit.tourism_article_management.presentation.controller.tour.TourCreation;

public class TourOperator {
    public HostedTour host(TourCreation tourCreation, Destination pickUp, Destination dropOff) {
        TourItinerary itinerary = TourItinerary.create(pickUp, dropOff, tourCreation.policy(), tourCreation.timeline());
        TourPresentation presentation = TourPresentation.create(tourCreation.title(), tourCreation.introduction(), tourCreation.images());
        return new HostedTour(itinerary, presentation);
    }
}

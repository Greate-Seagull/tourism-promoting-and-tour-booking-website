package com.uit.tourism_article_management.domain.tour;

import com.uit.tourism_article_management.presentation.controller.tour.Policy;
import com.uit.tourism_article_management.presentation.controller.tour.TimelineItem;

import java.util.List;

public class CompleteTour
{
    private String id;
    private String title;
    private String introduction;
    private List<String> images;
    private Description description;
    private String pickup;
    private String dropoff;
    private Policy policy;
    private List<TimelineItem> timeline;
    private List<Departure> schedules;
    private List<PriceTable> priceTables;
    private TourStatus status;
    
    public static CompleteTour fromHosted(HostedTour hosted) {
        CompleteTour completeTour = new CompleteTour();
        completeTour.id = hosted.id().value();
        completeTour.extractItinerary(hosted.itinerary());
        completeTour.extractPresentation(hosted.presentation());
    }

    private void extractItinerary(TourItinerary itinerary) {
        this.pickup = itinerary.getPickUp().name();
        this.dropoff = itinerary.getDropOff().name();
        this.policy = itinerary.getPolicy();
        this.timeline = itinerary.getTimeline();
    }

    private void extractPresentation(TourPresentation itinerary) {
        this.title = itinerary.getTitle();
        this.introduction = itinerary.getIntroduction();
        this.images = itinerary.getImages();
    }
}

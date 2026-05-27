package com.uit.tourism_article_management.domain.tour;

import com.uit.tourism_article_management.presentation.controller.tour.Policy;
import com.uit.tourism_article_management.presentation.controller.tour.TimelineItem;

import java.util.List;

public class TourItinerary {
    Destination pickUp;
    Destination dropOff;
    Policy policy;
    List<TimelineItem> timeline;

    public Destination getPickUp() {
        return pickUp;
    }

    public Destination getDropOff() {
        return dropOff;
    }

    public Policy getPolicy() {
        return policy;
    }

    public List<TimelineItem> getTimeline() {
        return timeline;
    }

    public static TourItinerary create(Destination pickUp, Destination dropOff, Policy policy, List<TimelineItem> timeline) {
        TourItinerary itinerary = new TourItinerary();
        itinerary.setPickUpAndDropOff(pickUp, dropOff);
        itinerary.setPolicy(policy);
        itinerary.setTimeline(timeline);
        return itinerary;
    }

    private void setTimeline(List<TimelineItem> timeline) {
        if (timeline.isEmpty())
            throw new RuntimeException("Timeline must not be empty");
        this.timeline = timeline;
    }

    private void setPolicy(Policy policy) {
        if (pickUp.isOversea(dropOff) && !policy.missingVisaPolicy())
            throw new RuntimeException("Visa policy must be provided for international travelling");
        this.policy = policy;
    }

    private void setPickUpAndDropOff(Destination pickUp, Destination dropOff) {
        if (pickUp == null)
            throw new RuntimeException("Pick-up destination must be provided");
        if (dropOff == null)
            throw new RuntimeException("Drop-off destination must be provided");
        if (pickUp == dropOff)
            throw new RuntimeException("Pick-up cannot be drop-off destination");
        this.pickUp = pickUp;
        this.dropOff = dropOff;
    }
}

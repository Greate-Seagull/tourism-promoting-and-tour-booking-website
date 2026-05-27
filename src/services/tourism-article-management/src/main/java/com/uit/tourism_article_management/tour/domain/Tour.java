package com.uit.tourism_article_management.tour.domain;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;
import com.uit.tourism_article_management.tour.presentation.view.TourCreation;
import com.uit.tourism_article_management.tour.presentation.view.TourModification;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class Tour extends TourRecord {
    private String id;
    private String title;
    private Set<String> images;
    private Destination pickUp;
    private Destination dropOff;
    private List<TimelineItem> timeline;
    private TourPolicy policy;
    private String accountId;
    private Description description;

    private Tour() {}

    public static Tour host(TourCreation request, Destination pickUp, Destination dropOff, TourOperator tourOperator) {
        Tour tour = new Tour();
        tour.id = UUID.randomUUID().toString();
        tour.setTitle(request.title());
        tour.setImages(request.images());
        tour.setDestinationsAndPolicy(pickUp, dropOff, request.policy());
        tour.setTimeline(request.timeline());
        tour.setOperator(tourOperator);
        return tour;
    }

    private void setOperator(TourOperator tourOperator) {
        if (this.accountId == null)
            throw new ClientException("Tour ownership cannot be transfered");
        this.accountId = tourOperator.getId();
    }

    protected void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new ClientException("Title must be provided");
        this.title = title;
    }

    protected void setImages(Set<String> images) {
        if (images == null || images.isEmpty())
            throw new ClientException("Images must be provided at least 1");
        this.images = images;
    }

    public void setDestinations(Destination pickUp, Destination dropOff) {
        if (pickUp == null || dropOff == null)
            throw new ClientException("Destinations must be provided");
        if (!pickUp.equals(dropOff))
            throw new ClientException("Destinations must not be the same place");
        this.pickUp = pickUp;
        this.dropOff = dropOff;
    }

    public void setTimeline(List<TimelineItem> timeline) {
        super.requireNotPublished();
        if (timeline == null || timeline.isEmpty())
            throw new ClientException("Timeline must be provided");
        this.timeline = timeline;
    }

    public void setDestinationsAndPolicy(Destination pickUp, Destination dropOff, TourPolicy policy) {
        this.setDestinations(pickUp, dropOff);
        this.setPolicy(policy);
    }

    private void setPolicy(TourPolicy policy) {
        super.requireNotPublished();
        if (policy == null)
            throw new ClientException("Tour policy must be provided");
        if (!this.pickUp.country().equals(this.dropOff.country()))
            TourPolicy.requireNonBlank("visa policy", policy.visaPolicy());
        this.policy = policy;
    }

    public void refine(TourModification request, TourOperator tourOperator) {
        requireOwnedBy(tourOperator);
        if (!request.getDescription().isAbsent())
            this.setDescription(request.getDescription().data());
        if (!request.getImages().isAbsent())
            this.setImages(request.getImages().data());
        if (!request.getPolicy().isAbsent())
            this.setPolicy(request.getPolicy().data());
        if (!request.getTimeline().isAbsent())
            this.setTimeline(request.getTimeline().data());
    }

    private void setDescription(Description data) {
        this.description = data;
    }

    public @Nullable String getId() {
        return this.id;
    }
}

package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.tour.domain.Description;
import com.uit.tourism_article_management.tour.domain.TimelineItem;
import com.uit.tourism_article_management.tour.domain.TourPolicy;
import com.uit.tourism_article_management.utils.Maybe;

import java.util.List;
import java.util.Set;

public class TourModification {
    private Maybe<Description> description = Maybe.absent();
    private Maybe<Set<String>> images = Maybe.absent();
    private Maybe<TourPolicy> policy = Maybe.absent();
    private Maybe<List<TimelineItem>> timeline = Maybe.absent();

    public Maybe<Description> getDescription() {
        return this.description;
    }

    public void setDescription(Description description) {
        if (description == null)
            this.description = Maybe.cleared();
        else
            this.description = Maybe.of(description);
    }

    public Maybe<Set<String>> getImages() {
        return this.images;
    }

    public void setImages(Set<String> images) {
        if (images == null)
            this.images = Maybe.cleared();
        else
            this.images = Maybe.of(images);
    }

    public Maybe<TourPolicy> getPolicy() {
        return this.policy;
    }

    public void setPolicy(TourPolicy policy) {
        if (policy == null)
            this.policy = Maybe.cleared();
        else
            this.policy = Maybe.of(policy);
    }

    public Maybe<List<TimelineItem>> getTimeline() {
        return this.timeline;
    }

    public void setTimeline(List<TimelineItem> timeline) {
        if (timeline == null)
            this.timeline = Maybe.cleared();
        else
            this.timeline = Maybe.of(timeline);
    }
}

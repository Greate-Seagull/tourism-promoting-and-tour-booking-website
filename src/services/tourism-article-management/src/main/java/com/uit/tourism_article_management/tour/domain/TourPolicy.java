package com.uit.tourism_article_management.tour.domain;

import com.uit.tourism_article_management.exception.ClientException;

public record TourPolicy(
        String inclusions,
        String exclusions,
        String childPricing,
        String paymentTerms,
        String bookingConditions,
        String changesAndCancellations,
        String standardCancellationPolicy,
        String holidayCancellationPolicy,
        String forceMajeure,
        String contact,
        String visaPolicy
) {
    public TourPolicy {
        requireNonBlank("inclusions", inclusions);
        requireNonBlank("exclusions", exclusions);
        requireNonBlank("child pricing", childPricing);
        requireNonBlank("payment terms", paymentTerms);
        requireNonBlank("booking conditions", bookingConditions);
        requireNonBlank("changes and cancellations", changesAndCancellations);
        requireNonBlank("standard cancellation policy", standardCancellationPolicy);
        requireNonBlank("holiday cancellation policy", holidayCancellationPolicy);
        requireNonBlank("force majeure", forceMajeure);
        requireNonBlank("contact", contact);
    }

    static public void requireNonBlank(String section, String field) {
        if (field == null || field.isBlank())
            throw new ClientException(String.format("Policy %s must be provided", section));
    }
}

package com.uit.tourism_article_management.presentation.controller.tour;

import javax.validation.constraints.NotNull;

public record Policy(
        @NotNull String inclusions,
        @NotNull String exclusion,
        @NotNull String childPricing,
        @NotNull String paymentTerms,
        @NotNull String bookingConditions,
        @NotNull String changesAndCancellations,
        @NotNull String standardCancellationPolicy,
        @NotNull String holidayCancellationPolicy,
        @NotNull String forceMajeure,
        @NotNull String contact,
        String visaPolicy
) {
    public boolean missingVisaPolicy() {
        return this.visaPolicy == null || this.visaPolicy.isBlank();
    }
}

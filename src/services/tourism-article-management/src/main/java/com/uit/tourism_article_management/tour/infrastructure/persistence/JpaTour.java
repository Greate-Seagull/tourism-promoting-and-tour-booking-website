package com.uit.tourism_article_management.tour.infrastructure.persistence;

import com.uit.tourism_article_management.tour.domain.TourStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tours")
@Data
public class JpaTour {
    @Id
    private String id;
    private String title;
    private String description;
    private String inclusions;
    private String exclusions;
    private String childPricing;
    private String paymentTerms;
    private String bookingConditions;
    private String changesAndCancellations;
    private String standardCancellationPolicy;
    private String holidayCancellationPolicy;
    private String forceMajeure;
    private String contact;
    private String visaPolicy;
    private TourStatus status;
}

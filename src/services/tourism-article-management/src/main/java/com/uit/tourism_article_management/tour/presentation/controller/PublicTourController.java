package com.uit.tourism_article_management.tour.presentation.controller;

import com.uit.tourism_article_management.tour.presentation.view.CompleteDeparture;
import com.uit.tourism_article_management.tour.presentation.view.CompleteRating;
import com.uit.tourism_article_management.tour.presentation.view.CompleteTour;
import com.uit.tourism_article_management.tour.presentation.view.ProductQuery;
import com.uit.tourism_article_management.utils.QueryDslPredicateBuilder;
import com.uit.tourism_article_management.tour.application.port.TourProjection;
import com.uit.tourism_article_management.tour.infrastructure.persistence.JpaTourRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/public/tours")
public class PublicTourController {
    private final JpaTourRepository repository;
    private final TourProjection projection;

    public PublicTourController(JpaTourRepository repository, TourProjection projection) {
        this.repository = repository;
        this.projection = projection;
    }

    @GetMapping
    public ResponseEntity search(
            @ModelAttribute ProductQuery query,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                this.repository.findAll(QueryDslPredicateBuilder.from(query), pageable)
        );
    }

    @GetMapping("/{tourId}")
    public ResponseEntity get(@PathVariable String tourId) {
        CompleteTour tour = this.projection.findCompleteById(tourId);
        return ResponseEntity.ok(tour);
    }

    @GetMapping("/{tourId}/ratings")
    public ResponseEntity getRatings(@PathVariable String tourId) {
        List<CompleteRating> ratings = this.projection.findRatingsOfTour(tourId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{tourId}/departures/{takeOffDate}")
    public ResponseEntity getDeparture(@PathVariable String tourId, @PathVariable LocalDate takeOffDate) {
        CompleteDeparture departure = this.projection.findDepartureScheduledAt(tourId, takeOffDate);
        return ResponseEntity.ok(departure);
    }
}

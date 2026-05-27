package com.uit.tourism_article_management.tour.presentation.controller;

import com.uit.tourism_article_management.security.SecurityUtils;
import com.uit.tourism_article_management.tour.application.TourCommandHandler;
import com.uit.tourism_article_management.tour.domain.rating.Rating;
import com.uit.tourism_article_management.tour.infrastructure.utils.MapstructTourMapper;
import com.uit.tourism_article_management.tour.presentation.view.RatingCreation;
import com.uit.tourism_article_management.tour.presentation.view.RatingModifiable;
import com.uit.tourism_article_management.tour.presentation.view.RatingModification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me/tours")
public class MyTourController {
    private final TourCommandHandler commandHandler;
    private final MapstructTourMapper mapper;

    public MyTourController(TourCommandHandler commandHandler, MapstructTourMapper mapper) {
        this.commandHandler = commandHandler;
        this.mapper = mapper;
    }

    @PostMapping("/{tourId}/ratings")
    public ResponseEntity rate(
            @PathVariable String tourId,
            @RequestBody RatingCreation request
    ) {
        this.commandHandler.rate(tourId, request, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tourId}/ratings")
    public ResponseEntity patch(
            @PathVariable String tourId,
            @RequestBody RatingModification modification
    ) {
        Rating rating = this.commandHandler.adjustRating(tourId, modification, SecurityUtils.getRequiredAccountId());
        RatingModifiable modifiable = this.mapper.toModifiable(rating);
        return ResponseEntity.ok(modifiable);
    }
}

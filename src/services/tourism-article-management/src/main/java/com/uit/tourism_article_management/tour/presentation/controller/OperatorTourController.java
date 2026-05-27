package com.uit.tourism_article_management.tour.presentation.controller;

import com.uit.tourism_article_management.order.domain.Tourist;
import com.uit.tourism_article_management.security.SecurityUtils;
import com.uit.tourism_article_management.tour.application.TourCommandHandler;
import com.uit.tourism_article_management.tour.application.port.TourProjection;
import com.uit.tourism_article_management.tour.domain.Tour;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;
import com.uit.tourism_article_management.tour.infrastructure.persistence.JpaTourRepository;
import com.uit.tourism_article_management.tour.infrastructure.utils.MapstructTourMapper;
import com.uit.tourism_article_management.tour.presentation.view.*;
import com.uit.tourism_article_management.utils.QueryDslPredicateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/operator/tours")
public class OperatorTourController {
    private final TourCommandHandler commandHandler;
    private final MapstructTourMapper mapper;
    private final TourProjection projection;
    private final JpaTourRepository repository;

    public OperatorTourController(TourCommandHandler commandHandler, MapstructTourMapper mapper, TourProjection projection, JpaTourRepository repository) {
        this.commandHandler = commandHandler;
        this.mapper = mapper;
        this.projection = projection;
        this.repository = repository;
    }

    @PostMapping
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity host(@RequestBody TourCreation request) {
        Tour tour = this.commandHandler.host(request, SecurityUtils.getRequiredAccountId());
        TourCreation creation = this.mapper.toCreation(tour);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{tourId}")
                .buildAndExpand(tour.getId())
                .toUri();

        return ResponseEntity.created(location).body(creation);
    }

    @DeleteMapping("/{tourId}")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity cancel(@PathVariable String tourId) {
        this.commandHandler.cancel(tourId, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tourId}/publish")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity publish(@PathVariable String tourId) {
        this.commandHandler.publish(tourId, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tourId}/unpublish")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity unpublish(@PathVariable String tourId) {
        this.commandHandler.unpublish(tourId, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tourId}/departures")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity schedule(
            @PathVariable String tourId,
            @RequestBody DepartureCreation request
    ) {
        this.commandHandler.schedule(tourId, request, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tourId}/price-tables")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity composePrice(
            @PathVariable String tourId,
            @RequestBody PriceTable request
    ) {
        this.commandHandler.composePrice(tourId, request, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tourId}")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity refine(
            @PathVariable String tourId,
            @RequestBody TourModification request
    ) {
        Tour tour = this.commandHandler.refine(tourId, request, SecurityUtils.getRequiredAccountId());
        TourModifiable modifiable = this.mapper.toModifiable(tour);
        return ResponseEntity.ok(modifiable);
    }

    @DeleteMapping("/{tourId}/departures/{takeOffDate}")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity cancelDeparture(
            @PathVariable String tourId,
            @PathVariable LocalDate takeOffDate
    ) {
        this.commandHandler.cancelDeparture(tourId, takeOffDate, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tourId}/price-tables/{name}")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity removePrice(
            @PathVariable String tourId,
            @PathVariable String name
    ) {
        this.commandHandler.removePrice(tourId, name, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tourId}/departures/{takeOffDate}/tourists")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity removePrice(
            @PathVariable String tourId,
            @PathVariable LocalDate takeOffDate
    ) {
        List<Tourist> tourists = this.projection.findTouristsOfDeparture(tourId, takeOffDate);
        return ResponseEntity.ok(tourists);
    }

    @GetMapping
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity search(
            @ModelAttribute OperatorTourQuery query,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                this.repository.findAll(QueryDslPredicateBuilder.from(query), pageable)
        );
    }

    @GetMapping("/{tourId}/price-tables")
    @PreAuthorize("hasRole('TOUR_OPERATOR')")
    public ResponseEntity getAllPriceTables(@PathVariable String tourId) {
        List<PriceTable> prices = this.projection.findPricesOfTour(tourId);
        return ResponseEntity.ok(prices);
    }
}

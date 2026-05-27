package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.tour.domain.departure.SeatPolicy;
import com.uit.tourism_article_management.tour.domain.departure.Stay;
import com.uit.tourism_article_management.tour.domain.departure.Transit;
import com.uit.tourism_article_management.tour.domain.departure.TransitTime;

import java.time.LocalDate;

public record DepartureCreation(
        LocalDate takeOffDate,
        Stay stay,
        Transit transit,
        String priceId,
        TransitTime arrivalTime,
        TransitTime returnTime,
        SeatPolicy seatPolicy,
        String note
) {
}

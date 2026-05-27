package com.uit.tourism_article_management.tour.presentation.view;

import com.uit.tourism_article_management.tour.domain.departure.SeatPolicy;
import com.uit.tourism_article_management.tour.domain.departure.Stay;
import com.uit.tourism_article_management.tour.domain.departure.Transit;
import com.uit.tourism_article_management.tour.domain.departure.TransitTime;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;

import java.time.LocalDate;

public record CompleteDeparture(
        LocalDate takeOffDate,
        int reservedSeats,
        SeatPolicy seatPolicy,
        TransitTime arrivalTime,
        TransitTime returnTime,
        PriceTable price,
        Stay stay,
        Transit transit,
        String note
) {
}

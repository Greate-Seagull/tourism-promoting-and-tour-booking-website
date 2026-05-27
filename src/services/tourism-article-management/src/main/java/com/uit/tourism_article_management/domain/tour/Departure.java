package com.uit.tourism_article_management.domain.tour;

public record Departure(
        String takeOffDate,
        SeatPolicy seatPolicy,
        TimePeriod arrivalTime,
        TimePeriod returnTime,
        Stay stay,
        Transit transit,
        String priceTableId,
        String note
) {
}

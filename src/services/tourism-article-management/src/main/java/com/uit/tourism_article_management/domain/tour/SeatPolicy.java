package com.uit.tourism_article_management.domain.tour;

public record SeatPolicy(
        int capacity,
        int minimumAge,
        int reservedSeats
) {
}

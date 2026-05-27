package com.uit.tourism_article_management.tour.domain.departure;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.order.domain.Tourist;

public record SeatPolicy(
        int seatCapacity,
        int minimumAgeForSeat
) {
    public SeatPolicy {
        requireMinimumCapacity(seatCapacity);
        requireMinimumAgeForSeat(minimumAgeForSeat);
    }

    static private void requireMinimumAgeForSeat(Integer age) {
        if (age == null)
            throw new ClientException("Departure minimum age for a seat must be provided");
        if (age < 1)
            throw new ClientException("Minimum age for a seat must be at least 1");
    }

    static private void requireMinimumCapacity(Integer capacity) {
        if (capacity == null)
            throw new ClientException("Departure seat capacity must be provided");
        if (capacity < 1)
            throw new ClientException("Departure seat capacity must be at least 1");
    }

    public boolean countAsSeat(Tourist tourist) {
        return tourist.getAge() >= this.minimumAgeForSeat;
    }

    public boolean isEnough(int seatCount) {
        return this.seatCapacity >= seatCount;
    }
}

package com.uit.tourism_article_management.tour.domain.departure;

import com.uit.tourism_article_management.exception.ClientException;

public record Stay(
        int dayCount,
        int nightCount
) {
    public Stay {
        requireMinimumDayCount(dayCount);
        requireMinimumNightCount(nightCount);
        requireMinimumDifference(dayCount, nightCount);
    }

    public Stay(int dayCount) {
        this(dayCount, dayCount - 1);
    }

    static private void requireMinimumDifference(int dayCount, int nightCount) {
        if (Math.abs(dayCount - nightCount) > 1)
            throw new ClientException("The difference between day count and night count must not exceed 1");
    }

    static private void requireMinimumNightCount(int nightCount) {
        if (nightCount < 1)
            throw new ClientException("Night count must be greater than 0");
    }

    static private void requireMinimumDayCount(int dayCount) {
        if (dayCount < 1)
            throw new ClientException("Day count must be greater than 0");
    }
}

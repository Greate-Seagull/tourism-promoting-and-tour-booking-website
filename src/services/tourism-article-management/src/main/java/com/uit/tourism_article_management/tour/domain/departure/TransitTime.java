package com.uit.tourism_article_management.tour.domain.departure;

import java.time.LocalTime;

public record TransitTime(
        LocalTime from,
        LocalTime to
) {

}

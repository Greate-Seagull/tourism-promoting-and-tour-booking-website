package com.uit.tourism_article_management.tour.domain.order;

import java.time.LocalDate;

public record Tourist(
    String fullname,
    Gender gender,
    LocalDate dateOfBirth,
    boolean wantSingleRoom
) {
}

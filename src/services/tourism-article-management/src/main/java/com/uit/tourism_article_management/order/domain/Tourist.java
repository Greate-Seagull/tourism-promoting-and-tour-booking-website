package com.uit.tourism_article_management.order.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Tourist(
        String fullname,
        Gender gender,
        LocalDate dateOfBirth,
        boolean wantSingleRoom
) {
    public int getAge() {
        return Math.toIntExact(ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now()));
    }
}

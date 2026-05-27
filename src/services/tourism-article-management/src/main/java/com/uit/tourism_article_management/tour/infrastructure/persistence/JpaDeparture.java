package com.uit.tourism_article_management.tour.infrastructure.persistence;

import com.uit.tourism_article_management.tour.domain.departure.Transit;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "departures")
@Data
public class JpaDeparture {
    private ZonedDateTime takeOffDate;
    private String tourId;
    private int dayCount;
    private int nightCount;
    private int capacity;
    private int reservedSeats;
    private int minimumAgeForSeats;
    private String note;
    private LocalTime arrivalTimeFrom;
    private LocalTime arrivalTimeTo;
    private LocalTime returnTimeFrom;
    private LocalTime returnTimeTo;
    private Transit transit;
}

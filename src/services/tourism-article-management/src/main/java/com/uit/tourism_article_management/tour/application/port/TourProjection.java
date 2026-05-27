package com.uit.tourism_article_management.tour.application.port;

import com.uit.tourism_article_management.tour.domain.order.Tourist;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;
import com.uit.tourism_article_management.tour.presentation.view.CompleteDeparture;
import com.uit.tourism_article_management.tour.presentation.view.CompleteRating;
import com.uit.tourism_article_management.tour.presentation.view.CompleteTour;

import java.time.LocalDate;
import java.util.List;

public interface TourProjection {
    CompleteTour findCompleteById(String tourId);
    List<CompleteRating> findRatingsOfTour(String tourId);

    List<Tourist> findTouristsOfDeparture(String tourId, LocalDate takeOffDate);

    CompleteDeparture findDepartureScheduledAt(String tourId, LocalDate takeOffDate);

    List<PriceTable> findPricesOfTour(String tourId);
}

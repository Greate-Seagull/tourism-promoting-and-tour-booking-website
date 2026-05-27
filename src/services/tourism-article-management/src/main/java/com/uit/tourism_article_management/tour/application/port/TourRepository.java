package com.uit.tourism_article_management.tour.application.port;

import com.uit.tourism_article_management.tour.domain.Tour;
import com.uit.tourism_article_management.tour.domain.TourRecord;
import com.uit.tourism_article_management.tour.domain.TourSchedule;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;

import java.util.Optional;

public interface TourRepository {
    void save(Tour tour);

    Optional<TourSchedule> getScheduleById(String tourId, String accountId);

    void delete(String tourId);

    void save(TourSchedule schedule);

    Optional<TourSchedule> getScheduleById(String tourId);

    Optional<PriceTable> getPriceTableById(String priceId);

    Optional<TourRecord> getRecordById(String tourId, String accountId);

    boolean existsPriceTableByName(String name, String tourId);

    void save(PriceTable priceTable);

    Optional<Tour> getItineraryById(String tourId, String accountId);

    void deletePriceTableByName(String name);
}

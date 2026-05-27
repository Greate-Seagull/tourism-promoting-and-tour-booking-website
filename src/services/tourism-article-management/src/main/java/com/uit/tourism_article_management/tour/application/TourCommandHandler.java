package com.uit.tourism_article_management.tour.application;


import com.uit.tourism_article_management.account.application.port.AccountRepository;
import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.order.domain.Order;
import com.uit.tourism_article_management.tour.application.port.DestinationRepository;
import com.uit.tourism_article_management.tour.application.port.OrderRepository;
import com.uit.tourism_article_management.tour.application.port.RatingRepository;
import com.uit.tourism_article_management.tour.application.port.TourRepository;
import com.uit.tourism_article_management.tour.domain.Destination;
import com.uit.tourism_article_management.tour.domain.Tour;
import com.uit.tourism_article_management.tour.domain.TourRecord;
import com.uit.tourism_article_management.tour.domain.TourSchedule;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;
import com.uit.tourism_article_management.tour.domain.rating.Rating;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;
import com.uit.tourism_article_management.tour.presentation.view.*;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TourCommandHandler {
    private final DestinationRepository destinationRepository;
    private final AccountRepository accountRepository;
    private final TourRepository tourRepository;
    private final OrderRepository orderRepository;
    private final RatingRepository ratingRepository;

    public TourCommandHandler(
            DestinationRepository destinationRepository,
            AccountRepository accountRepository,
            TourRepository tourRepository,
            OrderRepository orderRepository,
            RatingRepository ratingRepository
    ) {
        this.destinationRepository = destinationRepository;
        this.accountRepository = accountRepository;
        this.tourRepository = tourRepository;
        this.orderRepository = orderRepository;
        this.ratingRepository = ratingRepository;
    }

    public Tour host(TourCreation request, String accountId) {
        TourOperator tourOperator = this.getTourOperator(accountId);
        Destination pickUp = this.destinationRepository.getByName(request.pickUp().toLowerCase())
                .orElseThrow(() -> new ClientException("Destination pick-up does not exist"));
        Destination dropOff = this.destinationRepository.getByName(request.dropOff().toLowerCase())
                .orElseThrow(() -> new ClientException("Destination drop-off does not exist"));
        Tour tour = Tour.host(request, pickUp, dropOff, tourOperator);
        this.tourRepository.save(tour);
        return tour;
    }

    private @NonNull TourOperator getTourOperator(String accountId) {
        return this.accountRepository.getTourOperatorById(accountId)
                .orElseThrow(() -> new ClientException("You are not tour operator"));
    }

    public void cancel(String tourId, String accountId) {
        TourOperator tourOperator = this.getTourOperator(accountId);
        Optional<TourSchedule> optionalSchedule = this.tourRepository.getScheduleById(tourId, accountId);
        if (optionalSchedule.isEmpty())
            return;
        TourSchedule schedule = optionalSchedule.get();
        schedule.cancel(tourOperator);
        this.tourRepository.delete(tourId);
    }

    public void publish(String tourId, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        TourSchedule schedule = getSchedule(tourId, accountId);
        schedule.publish(tourOperator);
        this.tourRepository.save(schedule);
    }

    public void unpublish(String tourId, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        TourSchedule schedule = getSchedule(tourId, accountId);
        schedule.unpublish(tourOperator);
        this.tourRepository.save(schedule);
    }

    public void rate(String tourId, RatingCreation creation, String accountId) {
        boolean accountExist = this.accountRepository.existsById(accountId);
        if (accountExist)
            throw new ClientException("You are not allowed to perform this operation");
        TourSchedule schedule = this.tourRepository.getScheduleById(tourId)
                .orElseThrow(() -> new ClientException("Tour does not exist"));
        List<Order> orders = this.orderRepository.getByTourId(tourId);
        Rating rating = schedule.rate(creation, orders, accountId);
        this.ratingRepository.save(rating);
    }

    public void schedule(String tourId, DepartureCreation creation, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        PriceTable priceTable = this.tourRepository.getPriceTableById(creation.priceId())
                .orElseThrow(() -> new ClientException("Price table does not exist"));
        TourSchedule schedule = getSchedule(tourId, accountId);
        schedule.schedule(creation, priceTable, tourOperator);
        this.tourRepository.save(schedule);
    }

    private @NonNull TourSchedule getSchedule(String tourId, String accountId) {
        return this.tourRepository.getScheduleById(tourId, accountId)
                .orElseThrow(() -> new ClientException("You do not own this tour"));
    }

    public void composePrice(String tourId, PriceTable creation, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        boolean priceExist = this.tourRepository.existsPriceTableByName(creation.name(), tourId);
        if (priceExist)
            throw new ClientException("Price table name has conflicted with one of tour's price tables");
        TourRecord rec = this.tourRepository.getRecordById(tourId, accountId).
                orElseThrow(() -> new ClientException("You do not own this tour"));
        PriceTable priceTable = rec.composePrice(creation, tourOperator);
        this.tourRepository.save(priceTable);
    }

    public Tour refine(String tourId, TourModification request, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        Tour tour = this.tourRepository.getItineraryById(tourId, accountId)
                .orElseThrow(() -> new ClientException("You do not own this tour"));
        tour.refine(request, tourOperator);
        this.tourRepository.save(tour);
        return tour;
    }

    public void cancelDeparture(String tourId, LocalDate takeOffDate, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        TourSchedule schedule = getSchedule(tourId, accountId);
        schedule.cancelDeparture(takeOffDate, tourOperator);
        this.tourRepository.save(schedule);
    }

    public void removePrice(String tourId, String name, String accountId) {
        boolean priceExist = this.tourRepository.existsPriceTableByName(name, tourId);
        if (priceExist)
            return;
        TourOperator tourOperator = getTourOperator(accountId);
        TourSchedule schedule = getSchedule(tourId, accountId);
        schedule.removePrice(name, tourOperator);
        this.tourRepository.deletePriceTableByName(name);
    }

    public Rating adjustRating(String tourId, RatingModification modification, String accountId) {
        boolean accountExist = this.accountRepository.existsById(accountId);
        if (accountExist)
            throw new ClientException("You are not allowed to perform this operation");
        Rating rating = this.ratingRepository.getByTourIdAdnAccountId(tourId, accountId)
                .orElseThrow(() -> new ClientException("You have not rated this tour"));
        Rating adjusted = rating.adjust(modification);
        this.ratingRepository.save(adjusted);
        return adjusted;
    }
}

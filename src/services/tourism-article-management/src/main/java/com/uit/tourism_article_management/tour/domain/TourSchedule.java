package com.uit.tourism_article_management.tour.domain;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.tour.domain.departure.AvailableDeparture;
import com.uit.tourism_article_management.tour.domain.order.Order;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;
import com.uit.tourism_article_management.tour.domain.rating.Rating;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;
import com.uit.tourism_article_management.tour.presentation.view.DepartureCreation;
import com.uit.tourism_article_management.tour.presentation.view.RatingCreation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TourSchedule extends TourRecord {
    private String id;
    private List<AvailableDeparture> departures;

    public boolean hasActiveOrders() {
        return departures.stream().allMatch(AvailableDeparture::hasActiveOrders);
    }

    public boolean hasDepartures() {
        return !this.departures.isEmpty();
    }

    public void publish(TourOperator tourOperator) {
        if (super.isPublished())
            return;
        requireOwnedBy(tourOperator);
        requireVerifiedWallet(tourOperator);
        requireDeparturesScheduled();
        super.publish();
    }

    private void requireDeparturesScheduled() {
        if (!this.hasDepartures())
            throw new ClientException("Tour must have at least 1 departure scheduled");
    }

    private void requireVerifiedWallet(TourOperator tourOperator) {
        if (!tourOperator.hasVerifiedWallet())
            throw new ClientException("Your wallet must be verified");
    }

    public boolean isCancellable(TourOperator tourOperator) {
        return this.isOwnedBy(tourOperator) && !this.hasActiveOrders();
    }

    public void cancel(TourOperator tourOperator) {
        this.requireOwnedBy(tourOperator);
        this.requireNoActiveOrders();
    }

    private void requireNoActiveOrders() {
        if (this.hasActiveOrders())
            throw new ClientException("Tour must have no active orders");
    }

    public void unpublish(TourOperator tourOperator) {
        if (!super.isPublished())
            return;
        requireOwnedBy(tourOperator);
        requireNoPendingOrders();
        super.unpublish();
    }

    private void requireNoPendingOrders() {
        if (this.hasPendingOrders())
            throw new ClientException("Tour must have no pending orders");
    }

    private boolean hasPendingOrders() {
        return departures.stream().allMatch(AvailableDeparture::hasPendingOrders);
    }

    public Rating rate(RatingCreation creation, List<Order> orders, String accountId) {
        requirePublished();
        requireNotOwnedBy(accountId);
        List<Order> participatedIn = requireParticipatedIn(orders, accountId);
        requireDepartureEnded(participatedIn.stream().map(Order::getTakeOffDate).toList());
        return Rating.rate(creation, this.id, accountId);
    }

    private void requireDepartureEnded(List<LocalDate> takeOffDates) {
        Optional<AvailableDeparture> found = departures.stream()
                .filter(departure ->
                        departure.isTookOffAt(takeOffDates) && departure.hasEnded()
                )
                .findFirst();
        if (found.isEmpty())
            throw new ClientException("Departure has not ended");
    }

    private List<Order> requireParticipatedIn(List<Order> orders, String accountId) {
        List<Order> participatedOrders = orders.stream()
                .filter(order -> order.isOwnedBy(accountId))
                .toList();
        if (participatedOrders.isEmpty())
            throw new ClientException("You has not participated in this tour");
        return participatedOrders;
    }

    private void requireNoConfliction(DepartureCreation creation) {
        if (this.departures.stream()
                .anyMatch(departure -> departure.isTookOffAt(creation.takeOffDate()))
        )
            throw new ClientException("Tour has already been scheduled for this take off date");
    }

    public void schedule(DepartureCreation creation, PriceTable priceTable, TourOperator tourOperator) {
        requireOwnedBy(tourOperator);
        requireNoConfliction(creation);
        AvailableDeparture departure = new AvailableDeparture();
        departure.setTakeOffDate(creation.takeOffDate());
        departure.setStay(creation.stay());
        departure.setTransit(creation.transit());
        departure.setPrice(priceTable);
        departure.setArrivalTime(creation.arrivalTime());
        departure.setReturnTime(creation.returnTime());
        departure.setSeatPolicy(creation.seatPolicy());
        departure.setNote(creation.note());
        this.departures.add(departure);
    }

    public void cancelDeparture(LocalDate takeOffDate, TourOperator tourOperator) {
        requireOwnedBy(tourOperator);
        var found = this.departures.stream()
                .filter(departure -> departure.isTookOffAt(takeOffDate))
                .findFirst();
//                .orElseThrow(() -> {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                    return new ClientException(String.format("Tour was not scheduled on %s", takeOffDate.format(formatter)));
//                });
        if (found.isEmpty())
            return;
        var exist = found.get();
        exist.requireNoOrders();
        exist.requireNotEnded();
        requireDeparturesScheduled();
        this.departures.remove(exist);
    }

    public void removePrice(String name, TourOperator tourOperator) {
        requireOwnedBy(tourOperator);
        requireNoReferenced(name);
    }

    private void requireNoReferenced(String name) {
        List<String> errors = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(var departure: this.departures) {
            if (departure.isUsingPrice(name))
                errors.add(String.format("Price table %s is being used on schedule %s", name, departure.getTakeOffDate().format(formatter)));
        }
        if (!errors.isEmpty())
            throw new ClientException(errors);
    }
}

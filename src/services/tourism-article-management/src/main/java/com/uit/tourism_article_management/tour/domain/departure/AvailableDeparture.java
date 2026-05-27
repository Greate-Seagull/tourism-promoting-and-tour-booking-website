package com.uit.tourism_article_management.tour.domain.departure;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.tour.domain.price_table.PriceTable;

import java.time.LocalDate;
import java.util.List;

public class AvailableDeparture {
    private LocalDate takeOffDate;
    private int reservedSeats;
    private int registrationCount;
    private int dayCount;
    private TransitTime returnTime;
    private Stay stay;
    private Transit transit;
    private PriceTable price;
    private TransitTime arrivalTime;
    private SeatPolicy seatPolicy;
    private String note;

    public boolean hasActiveOrders() {
        return this.reservedSeats != 0;
    }

    public boolean hasPendingOrders() {
        return this.hasActiveOrders() && !this.hasEnded();
    }

    public boolean hasEnded() {
        return this.takeOffDate.plusDays(this.dayCount).with(returnTime.to()).isBefore(LocalDate.now());
    }

    public boolean isTookOffAt(List<LocalDate> takeOffDates) {
        return takeOffDates.contains(this.takeOffDate);
    }

    public void setTakeOffDate(LocalDate takeOffDate) {
        if (takeOffDate == null)
            throw new ClientException("Departure take off date must be provided");
        if (takeOffDate.isBefore(LocalDate.now()))
            throw new ClientException("Departure take off date must be in the future");
        this.takeOffDate = takeOffDate;
    }

    public boolean isTookOffAt(LocalDate takeOffDate) {
        return takeOffDate.equals(this.takeOffDate);
    }

    public void setStay(Stay stay) {
        if (stay == null)
            throw new ClientException("Departure stay must be provided");
        this.stay = stay;
    }

    public void setTransit(Transit transit) {
        if (transit == null)
            throw new ClientException("Departure transit must be provided");
        this.transit = transit;
    }

    public void setPrice(PriceTable price) {
        if (price == null)
            throw new ClientException("Departure price must be provided");
        this.price = price;
    }

    public void setArrivalTime(TransitTime arrivalTime) {
        if (arrivalTime == null)
            throw new ClientException("Departure arrival time must be provided");
        this.arrivalTime = arrivalTime;
    }

    public void setReturnTime(TransitTime returnTime) {
        if (returnTime == null)
            throw new ClientException("Departure return time must be provided");
        this.returnTime = returnTime;
    }

    public void setSeatPolicy(SeatPolicy seatPolicy) {
        if (seatPolicy == null)
            throw new ClientException("Departure seat policy must be provided");
        this.seatPolicy = seatPolicy;
    }

    public void setNote(String note) {
        if (note == null || note.isBlank())
            note = null;
        this.note = note;
    }

    public void requireNoOrders() {
        if (this.hasOrders())
            throw new ClientException("Departure must not have any orders");
    }

    private boolean hasOrders() {
        return this.registrationCount > 0;
    }

    public void requireNotEnded() {
        if (this.hasEnded())
            throw new ClientException("Departure has ended");
    }

    public LocalDate getTakeOffDate() {
        return this.takeOffDate;
    }

    public boolean isUsingPrice(String name) {
        return this.price.hasName(name);
    }
}

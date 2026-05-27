package com.uit.tourism_article_management.order.domain;

import com.uit.tourism_article_management.account.domain.Account;
import com.uit.tourism_article_management.account.domain.Email;
import com.uit.tourism_article_management.account.domain.PhoneNumber;
import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.order.presentation.view.OrderByAccountCreation;
import com.uit.tourism_article_management.order.presentation.view.OrderByContactCreation;
import com.uit.tourism_article_management.tour.domain.TourSchedule;
import com.uit.tourism_article_management.tour.domain.tour_operator.BankWallet;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;
import java.util.UUID;

public class Order {
    private String id;
    private String tourId;
    private LocalDate takeOffDate;
    private String accountId;
    private String fullname;
    private Email email;
    private PhoneNumber phoneNumber;
    private long price;
    private String transactionId;
    private String receivedBankAccountId;
    private OrderStatus status;

    private Order() {
    }

    public static Order initiate(OrderByAccountCreation creation, TourSchedule schedule, TourOperator tourOperator, Account representative) {
        tourOperator.requireVerifiedWallet();
        long price = schedule.book(creation.takeOffDate(), creation.tourists(), representative.getId());
        Order order = createOrder(creation.tourId(), creation.takeOffDate(), tourOperator.getWallet(), price);
        order.setRepresentative(representative);
        return order;
    }

    private static @NonNull Order createOrder(String tourId, LocalDate takeOffDate, BankWallet wallet, long price) {
        Order order = new Order();
        order.id = UUID.randomUUID().toString();
        order.setTour(tourId, takeOffDate);
        order.setPrice(price);
        order.setReceiver(wallet);
        order.setUnpaid();
        return order;
    }

    public static Order initiate(OrderByContactCreation creation, TourSchedule schedule, TourOperator tourOperator) {
        tourOperator.requireVerifiedWallet();
        long price = schedule.book(creation.takeOffDate(), creation.tourists(), null);
        Order order = createOrder(creation.tourId(), creation.takeOffDate(), tourOperator.getWallet(), price);
        order.setRepresentative(creation.representative());
        return order;
    }

    private void setReceiver(BankWallet wallet) {
        this.receivedBankAccountId = wallet.getBankAccountId();
    }

    private void setRepresentative(Representative representative) {
        if (representative == null)
            throw new ClientException("Order representative must be provided if you do not use you account");
        this.accountId = null;
        this.fullname = representative.fullname();
        this.email = representative.email();
        this.phoneNumber = representative.phoneNumber();
    }

    private void setUnpaid() {
        this.transactionId = null;
        this.status = OrderStatus.UNPAID;
    }

    private void setPrice(long price) {
        this.price = price;
    }

    private void setRepresentative(@NonNull Account representative) {
        this.accountId = representative.getId();
        this.fullname = representative.getFullname();
        this.email = representative.getEmail();
        this.phoneNumber = representative.getPhoneNumber();
    }

    private void setTour(@NonNull String tourId, @NonNull LocalDate takeOffDate) {
        this.tourId = tourId;
        this.takeOffDate = takeOffDate;
    }

    public LocalDate getTakeOffDate() {
        return this.takeOffDate;
    }

    public boolean isOwnedBy(String accountId) {
        return this.accountId.equals(accountId);
    }

    public void confirmedBy(Transaction transaction) {
        if (!this.isUnpaid())
            return;
        if (!transaction.isSucceededWith(this.price, this.receivedBankAccountId))
            return;
        this.setPaid(transaction);
    }

    private boolean isUnpaid() {
        return this.status == OrderStatus.UNPAID;
    }

    public String getTourId() {
        return this.tourId;
    }

    public void cancelledBy(TourSchedule schedule, Account account) {
        requireOwnedBy(account);
        requirePaid();
        schedule.requireRefundable(this.takeOffDate);
        this.setRefunding();
    }

    private void setRefunding() {
        this.status = OrderStatus.REFUNDING;
    }

    private void requireOwnedBy(Account account) {
        if (!this.accountId.equals(account.getId()))
            throw new ClientException("You do not have this order");
    }

    private void requirePaid() {
        if (!this.isPaid())
            throw new ClientException("Your order has not been paid");
    }

    private boolean isPaid() {
        return this.status == OrderStatus.PAID;
    }

    private void setPaid(Transaction transaction) {
        this.transactionId = transaction.getId();
        this.status = OrderStatus.PAID;
    }

    public void refundedBy(Transaction transaction) {
        if (!this.isRefunding())
            return;
//            throw new InternalException(String.format("Order %s has not been in refunding state before refunded", this.id));

        if (!transaction.isRefunded())
            return;
        this.setRefunded();
    }

    private void setRefunded() {
        this.status = OrderStatus.REFUNDED;
    }

    private boolean isRefunding() {
        return this.status == OrderStatus.REFUNDING;
    }
}

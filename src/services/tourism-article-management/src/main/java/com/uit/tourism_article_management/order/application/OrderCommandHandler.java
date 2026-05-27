package com.uit.tourism_article_management.order.application;

import com.uit.tourism_article_management.account.application.port.AccountRepository;
import com.uit.tourism_article_management.account.application.port.CryptoService;
import com.uit.tourism_article_management.account.domain.Account;
import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.exception.InternalException;
import com.uit.tourism_article_management.order.application.port.PaymentRepository;
import com.uit.tourism_article_management.order.domain.Order;
import com.uit.tourism_article_management.order.domain.Transaction;
import com.uit.tourism_article_management.order.presentation.view.OrderByAccountCreation;
import com.uit.tourism_article_management.order.presentation.view.OrderByContactCreation;
import com.uit.tourism_article_management.tour.application.port.OrderRepository;
import com.uit.tourism_article_management.tour.application.port.TourRepository;
import com.uit.tourism_article_management.tour.domain.TourSchedule;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class OrderCommandHandler {
    private final TourRepository tourRepository;
    private final AccountRepository accountRepository;
    private final OrderRepository orderRepository;
    private final CryptoService cryptoService;
    private final PaymentRepository paymentRepository;

    public OrderCommandHandler(TourRepository tourRepository, AccountRepository accountRepository, OrderRepository orderRepository, CryptoService cryptoService, PaymentRepository paymentRepository) {
        this.tourRepository = tourRepository;
        this.accountRepository = accountRepository;
        this.orderRepository = orderRepository;
        this.cryptoService = cryptoService;
        this.paymentRepository = paymentRepository;
    }

    public String initiate(OrderByAccountCreation creation, String accountId) {
        Account representative = getAccount(accountId);
        TourSchedule schedule = getTourSchedule(creation.tourId());
        TourOperator tourOperator = getTourOperator(schedule);
        Order order = Order.initiate(creation, schedule, tourOperator, representative);
        this.orderRepository.save(order);
        String signature = this.cryptoService.hashSignature(order);
        return this.paymentRepository.getQr(order, tourOperator.getWallet(), signature);
    }

    private @NonNull Account getAccount(String accountId) {
        return this.accountRepository.getById(accountId)
                .orElseThrow(() -> new ClientException("Account has been deleted or banned"));
    }

    public String initiate(OrderByContactCreation creation) {
        TourSchedule schedule = getTourSchedule(creation.tourId());
        TourOperator tourOperator = getTourOperator(schedule);
        Order order = Order.initiate(creation, schedule, tourOperator);
        this.orderRepository.save(order);
        String signature = this.cryptoService.hashSignature(order);
        return this.paymentRepository.getQr(order, tourOperator.getWallet(), signature);
    }

    private @NonNull TourSchedule getTourSchedule(String tourId) {
        return this.tourRepository.getScheduleById(tourId)
                .orElseThrow(() -> new ClientException("Tour does not exist"));
    }

    private @NonNull TourOperator getTourOperator(TourSchedule schedule) {
        return this.accountRepository.getTourOperatorById(schedule.getOwnerId())
                .orElseThrow(() -> new InternalException("Tour operator has been deleted"));
    }

    public void pay(@NonNull String orderId, String signature, String transactionId) {
        Optional<Order> unknownOrder = this.orderRepository.getById(orderId);
        if (unknownOrder.isEmpty())
            return;
        Order order = unknownOrder.get();
        if (!this.cryptoService.compareSignature(order, signature))
            return;
        Optional<Transaction> unknownTransaction = this.paymentRepository.getById(transactionId);
        if (unknownTransaction.isEmpty())
            return;
        Transaction transaction = unknownTransaction.get();
        order.confirmedBy(transaction);
        this.orderRepository.save(order);
    }

    public void cancel(String orderId, String accountId) {
        Account account = getAccount(accountId);
        Order order = this.orderRepository.getById(orderId)
                .orElseThrow(() -> new ClientException("You do not have this order"));
        TourSchedule schedule = getTourSchedule(order.getTourId());
        order.cancelledBy(schedule, account);
        this.orderRepository.save(order);
        String signature = this.cryptoService.hashSignature(order);
        this.paymentRepository.requestRefund(order, signature);
    }

    public void refund(@NonNull String orderId, String signature, String transactionId) {
        Optional<Order> unknownOrder = this.orderRepository.getById(orderId);
        if (unknownOrder.isEmpty())
            return;
        Order order = unknownOrder.get();
        if (!this.cryptoService.compareSignature(order, signature))
            return;
        Optional<Transaction> unknownTransaction = this.paymentRepository.getById(transactionId);
        if (unknownTransaction.isEmpty())
            return;
        Transaction transaction = unknownTransaction.get();
        order.refundedBy(transaction);
        this.orderRepository.save(order);
    }
}

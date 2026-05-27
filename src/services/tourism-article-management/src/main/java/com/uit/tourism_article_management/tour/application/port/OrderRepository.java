package com.uit.tourism_article_management.tour.application.port;

import com.uit.tourism_article_management.order.domain.Order;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> getByTourId(String tourId);

    void save(Order order);

    Optional<Order> getById(@NonNull String orderId);
}

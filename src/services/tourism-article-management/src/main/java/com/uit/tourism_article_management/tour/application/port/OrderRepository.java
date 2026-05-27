package com.uit.tourism_article_management.tour.application.port;

import com.uit.tourism_article_management.tour.domain.order.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> getByTourId(String tourId);
}

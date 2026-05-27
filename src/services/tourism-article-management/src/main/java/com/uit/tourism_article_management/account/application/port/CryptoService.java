package com.uit.tourism_article_management.account.application.port;

import com.uit.tourism_article_management.order.domain.Order;

public interface CryptoService {
    String hashPassword(String password);

    boolean comparePassword(String password);

    String hashSignature(Order order);

    boolean compareSignature(Order order, String signature);
}

package com.uit.tourism_article_management.order.presentation.controller;

import com.uit.tourism_article_management.order.application.OrderCommandHandler;
import com.uit.tourism_article_management.order.presentation.view.OrderByContactCreation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/orders")
public class PublicOrderController {
    private final OrderCommandHandler commandHandler;

    public PublicOrderController(OrderCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping
    public ResponseEntity book(@RequestBody OrderByContactCreation creation) {
        String qr = this.commandHandler.initiate(creation);
        return ResponseEntity.ok(qr);
    }
}

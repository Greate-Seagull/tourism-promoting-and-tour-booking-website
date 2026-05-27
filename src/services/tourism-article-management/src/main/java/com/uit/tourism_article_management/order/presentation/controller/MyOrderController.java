package com.uit.tourism_article_management.order.presentation.controller;

import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.order.application.OrderCommandHandler;
import com.uit.tourism_article_management.order.application.port.OrderProjection;
import com.uit.tourism_article_management.order.infrastructure.persitence.JpaOrderRepository;
import com.uit.tourism_article_management.order.presentation.view.CompleteOrder;
import com.uit.tourism_article_management.order.presentation.view.OrderByAccountCreation;
import com.uit.tourism_article_management.order.presentation.view.OrderQuery;
import com.uit.tourism_article_management.security.SecurityUtils;
import com.uit.tourism_article_management.utils.QueryDslPredicateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me/orders")
public class MyOrderController {
    private final OrderCommandHandler commandHandler;
    private final OrderProjection projection;
    private final JpaOrderRepository repository;

    public MyOrderController(OrderCommandHandler commandHandler, OrderProjection projection, JpaOrderRepository repository) {
        this.commandHandler = commandHandler;
        this.projection = projection;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity book(@RequestBody OrderByAccountCreation creation) {
        String qr = this.commandHandler.initiate(creation, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.ok(qr);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity get(@PathVariable String orderId) {
        CompleteOrder order = this.projection.getById(orderId, SecurityUtils.getRequiredAccountId())
                .orElseThrow(() -> new ClientException("Order does not exist"));
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity cancel(@PathVariable String orderId) {
        this.commandHandler.cancel(orderId, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity search(
            @ModelAttribute OrderQuery query,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                this.repository.findAll(QueryDslPredicateBuilder.from(query, SecurityUtils.getRequiredAccountId()), pageable)
        );
    }
}

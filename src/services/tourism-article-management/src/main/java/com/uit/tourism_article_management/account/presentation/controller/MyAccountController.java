package com.uit.tourism_article_management.account.presentation.controller;

import com.uit.tourism_article_management.account.domain.RoleRequest;
import com.uit.tourism_article_management.account.application.AccountCommandHandler;
import com.uit.tourism_article_management.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/me/accounts")
public class MyAccountController {
    private final AccountCommandHandler commandHandler;

    public MyAccountController(AccountCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping("/role-requests")
    public ResponseEntity apply(@RequestBody RoleRequest creation) {
        this.commandHandler.apply(creation, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }
}

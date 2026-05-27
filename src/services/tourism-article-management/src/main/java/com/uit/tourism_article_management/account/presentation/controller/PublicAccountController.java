package com.uit.tourism_article_management.account.presentation.controller;

import com.uit.tourism_article_management.account.application.AccountCommandHandler;
import com.uit.tourism_article_management.account.presentation.view.AccountCreation;
import com.uit.tourism_article_management.account.presentation.view.AccountSigning;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/auth")
public class PublicAccountController {
    private final AccountCommandHandler commandHandler;

    public PublicAccountController(AccountCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody AccountCreation creation) {
        String token = this.commandHandler.signUp(creation);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AccountSigning signing) {
        String token = this.commandHandler.signIn(signing);
        return ResponseEntity.ok(token);
    }
}

package com.uit.tourism_article_management.account.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/accounts")
public class AdminAccountController {
    @GetMapping("/role-requests")
    public ResponseEntity searchForRoleRequests() {

    }


}

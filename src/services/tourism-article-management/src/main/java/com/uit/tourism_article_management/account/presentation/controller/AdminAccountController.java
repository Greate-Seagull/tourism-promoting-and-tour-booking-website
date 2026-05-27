package com.uit.tourism_article_management.account.presentation.controller;

import com.uit.tourism_article_management.account.application.AccountCommandHandler;
import com.uit.tourism_article_management.account.infrastructure.persitence.JpaRoleRequestRepository;
import com.uit.tourism_article_management.account.presentation.view.AccountRoleModification;
import com.uit.tourism_article_management.account.presentation.view.Reason;
import com.uit.tourism_article_management.account.presentation.view.RoleRequestQuery;
import com.uit.tourism_article_management.security.SecurityUtils;
import com.uit.tourism_article_management.utils.QueryDslPredicateBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/accounts")
public class AdminAccountController {
    private final JpaRoleRequestRepository repository;
    private final AccountCommandHandler commandHandler;

    public AdminAccountController(JpaRoleRequestRepository repository, AccountCommandHandler commandHandler) {
        this.repository = repository;
        this.commandHandler = commandHandler;
    }

    @GetMapping("/role-requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity searchForRoleRequests(
            @ModelAttribute RoleRequestQuery query,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                this.repository.findAll(QueryDslPredicateBuilder.from(query), pageable)
        );
    }

    @PostMapping("/role-requests/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity reject(@PathVariable Long id, @RequestBody Reason body) {
        this.commandHandler.reject(id, body, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/role-requests/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity approve(@PathVariable Long id, @RequestBody Reason body) {
        this.commandHandler.approve(id, body, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/ban")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity ban(@PathVariable String accountId, @RequestBody Reason body) {
        this.commandHandler.ban(accountId, body, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/demote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity demote(@PathVariable String accountId, @RequestBody AccountRoleModification modification) {
        this.commandHandler.demote(accountId, modification, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity promote(@PathVariable String accountId, @RequestBody AccountRoleModification modification) {
        this.commandHandler.promote(accountId, modification, SecurityUtils.getRequiredAccountId());
        return ResponseEntity.noContent().build();
    }
}

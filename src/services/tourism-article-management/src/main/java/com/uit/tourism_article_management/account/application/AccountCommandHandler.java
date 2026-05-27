package com.uit.tourism_article_management.account.application;

import com.uit.tourism_article_management.account.application.port.*;
import com.uit.tourism_article_management.account.domain.*;
import com.uit.tourism_article_management.account.presentation.view.AccountRoleModification;
import com.uit.tourism_article_management.account.presentation.view.Reason;
import com.uit.tourism_article_management.account.presentation.view.AccountCreation;
import com.uit.tourism_article_management.account.presentation.view.AccountSigning;
import com.uit.tourism_article_management.account.presentation.view.BankWalletSubmission;
import com.uit.tourism_article_management.exception.ClientException;
import com.uit.tourism_article_management.tour.domain.tour_operator.TourOperator;
import org.jspecify.annotations.NonNull;

public class AccountCommandHandler {
    private final AccountRepository accountRepository;
    private final CryptoService cryptoService;
    private final TokenService tokenService;
    private final BankGateway bankGateway;
    private final AdminAuditStore auditStore;

    public AccountCommandHandler(AccountRepository accountRepository, CryptoService cryptoService, TokenService tokenService, BankGateway bankGateway, AdminAuditStore auditStore) {
        this.accountRepository = accountRepository;
        this.cryptoService = cryptoService;
        this.tokenService = tokenService;
        this.bankGateway = bankGateway;
        this.auditStore = auditStore;
    }

    public String signUp(AccountCreation creation) {
        boolean emailOrPhoneExists = this.accountRepository.existsByPhoneOrEmail(creation.phoneNumber(), creation.email());
        if (emailOrPhoneExists)
            throw new ClientException("Phone number or email has been registered");
        String hashed = this.cryptoService.hash(creation.password());
        Account account = Account.create(creation, hashed);
        this.accountRepository.save(account);
        return this.tokenService.generate(account);
    }

    public String signIn(AccountSigning signing) {
        Account account = requireAccount(signing);
        if (!this.cryptoService.compare(account.getPassword()))
            throw new ClientException("Phone number or email or password is invalid");
        return this.tokenService.generate(account);
    }

    private Account requireAccount(AccountSigning signing) {
        if (PhoneNumber.isValid(signing.phoneOrEmail())) {
            return this.accountRepository.getByPhoneNumber(signing.phoneOrEmail())
                    .orElseThrow(() -> new ClientException("Phone number or email or password is invalid"));
        }
        else if (Email.isValid(signing.phoneOrEmail())) {
            return this.accountRepository.getByEmail(signing.phoneOrEmail())
                    .orElseThrow(() -> new ClientException("Phone number or email or password is invalid"));
        }
        else
            throw new ClientException("Phone number or email or password is invalid");
    }

    public void apply(RoleRequest creation, String accountId) {
        Account account = getAccount(accountId);
        RoleRequest created = account.apply(creation);
        this.accountRepository.save(created);
    }

    public String submitBankWallet(BankWalletSubmission submit, String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        String bankName = this.bankGateway.getName(submit)
                .orElseThrow(() -> new ClientException("Bank account does not exist"));
        tourOperator.submit(submit);
        this.accountRepository.save(tourOperator);
        return bankName;
    }

    private @NonNull TourOperator getTourOperator(String accountId) {
        return this.accountRepository.getTourOperatorById(accountId)
                .orElseThrow(() -> new ClientException("You are not tour operator"));
    }

    public void verifyBankWallet(String accountId) {
        TourOperator tourOperator = getTourOperator(accountId);
        tourOperator.verifyBankWallet();
        this.accountRepository.save(tourOperator);
    }

    public void reject(Long requestId, Reason message, String accountId) {
        Admin admin = getAdmin(accountId);
        RoleRequest roleRequest = getRoleRequest(requestId);
        roleRequest.rejectedBy(message, admin);
        this.accountRepository.save(roleRequest);
    }

    private @NonNull RoleRequest getRoleRequest(Long requestId) {
        return this.accountRepository.getRoleRequestById(requestId)
                .orElseThrow(() -> new ClientException("Role request does not exist"));
    }

    private @NonNull Admin getAdmin(String accountId) {
        return this.accountRepository.getAdminById(accountId)
                .orElseThrow(() -> new ClientException("You do not have permission to perform this operation"));
    }

    public void approve(Long requestId, Reason message, String accountId) {
        Admin admin = getAdmin(accountId);
        RoleRequest roleRequest = getRoleRequest(requestId);
        roleRequest.approvedBy(message, admin);
        this.accountRepository.save(roleRequest);
        Account account = getAccount(roleRequest.getIssuer());
        account.grant(roleRequest.getRole());
        this.accountRepository.save(account);
    }

    private @NonNull Account getAccount(String accountId) {
        return this.accountRepository.getById(accountId)
                .orElseThrow(() -> new ClientException("Account has been deleted or banned"));
    }

    public void ban(String accountId, Reason message, String adminId) {
        Admin admin = getAdmin(adminId);
        Account account = getAccount(accountId);
        account.banned();
        var audit = new AdminAction(admin.getId(), Action.BAN, account.getId(), message.reason());
        this.accountRepository.save(account);
        this.auditStore.save(audit);
    }

    public void demote(String accountId, AccountRoleModification modification, String adminId) {
        Admin admin = getAdmin(adminId);
        Account account = getAccount(accountId);
        account.removeRoles(modification.roles());
        var audit = new AdminAction(admin.getId(), Action.DEMOTE, account.getId(), modification.reason());
        this.accountRepository.save(account);
        this.auditStore.save(audit);
    }

    public void promote(String accountId, AccountRoleModification modification, String adminId) {
        Admin admin = getAdmin(adminId);
        Account account = getAccount(accountId);
        account.grantRoles(modification.roles());
        var audit = new AdminAction(admin.getId(), Action.PROMOTE, account.getId(), modification.reason());
        this.accountRepository.save(account);
        this.auditStore.save(audit);
    }
}

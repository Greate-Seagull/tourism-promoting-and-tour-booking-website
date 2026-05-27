package com.uit.tourism_article_management.account.application;

import com.uit.tourism_article_management.account.application.port.AccountRepository;
import com.uit.tourism_article_management.account.application.port.BankGateway;
import com.uit.tourism_article_management.account.application.port.CryptoService;
import com.uit.tourism_article_management.account.application.port.TokenService;
import com.uit.tourism_article_management.account.domain.Account;
import com.uit.tourism_article_management.account.domain.Email;
import com.uit.tourism_article_management.account.domain.PhoneNumber;
import com.uit.tourism_article_management.account.domain.RoleRequest;
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

    public AccountCommandHandler(AccountRepository accountRepository, CryptoService cryptoService, TokenService tokenService, BankGateway bankGateway) {
        this.accountRepository = accountRepository;
        this.cryptoService = cryptoService;
        this.tokenService = tokenService;
        this.bankGateway = bankGateway;
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
        Account account = this.accountRepository.getById(accountId)
                .orElseThrow(() -> new ClientException("Account has been deleted or banned"));
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
}

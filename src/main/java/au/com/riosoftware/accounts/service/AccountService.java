package au.com.riosoftware.accounts.service;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import au.com.riosoftware.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Mono<Account> createAccount(final CreateAccountRequest request) {
        return accountRepository.save(fromRequest(request));
    }

    public Account fromRequest(final CreateAccountRequest req) {
        return new Account(req.userId(), UUID.randomUUID().toString(), req.accountType());
    }

    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

}

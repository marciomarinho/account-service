package au.com.riosoftware.accounts.service;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import au.com.riosoftware.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountService {

    private final AccountRequestMapper mapper;
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(final AccountRequestMapper accountRequestMapper,
                          final AccountRepository accountRepository) {
        this.mapper = accountRequestMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Mono<Account> createAccount(final CreateAccountRequest request) {
        return accountRepository.save(mapper.fromRequest(request));
    }

    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }


    public Mono<Account> findById(String accountNumber) {
        return accountRepository.findById(accountNumber);
    }

    public Flux<Account> findByUserId(String userId) {
        return accountRepository.findByUserId(userId);
    }
}

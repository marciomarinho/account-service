package au.com.riosoftware.accounts.controller;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import au.com.riosoftware.accounts.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Account> createAccount(@Valid @RequestBody final CreateAccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @GetMapping(value = "/{accountNumber}")
    public Mono<Account> findById(@PathVariable final String accountNumber) {
        return accountService.findById(accountNumber);
    }

    @GetMapping(value = "/user/{userId}")
    public Flux<Account> findByUserId(@PathVariable final String userId) {
        return accountService.findByUserId(userId);
    }

    @GetMapping
    public Flux<Account> findAll() {
        return accountService.findAll();
    }

}

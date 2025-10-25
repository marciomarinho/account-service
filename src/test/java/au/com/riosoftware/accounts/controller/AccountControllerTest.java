package au.com.riosoftware.accounts.controller;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import au.com.riosoftware.accounts.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//@WebFluxTest(controllers = AccountController.class)
class AccountControllerTest {

    private AccountService accountService;
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        accountService = mock(AccountService.class);
        accountController = new AccountController(accountService);
    }

    @Test
    void testCreateAccount() {
        CreateAccountRequest request = new CreateAccountRequest(
                "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                "CHECKING",
                "USD"
        );

        Account expectedAccount = new Account(
                "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                "ACC-123456789",
                "CHECKING"
        );

        expectedAccount.setId("7b7a22df-a952-4b51-9403-66617417378a");
        expectedAccount.setCurrency("USD");
        expectedAccount.setBalance(new BigDecimal("0.00"));
        expectedAccount.setStatus("ACTIVE");

        when(accountService.createAccount(any()))
                .thenReturn(Mono.just(expectedAccount));

        Mono<Account> result = accountController.createAccount(request);
        StepVerifier.create(result)
                .expectNext(expectedAccount)
                .verifyComplete();
    }

}
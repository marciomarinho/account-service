package au.com.riosoftware.accounts.service;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import au.com.riosoftware.accounts.model.AccountBuilder;
import au.com.riosoftware.accounts.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    private AccountRequestMapper mapper;
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        mapper = mock(AccountRequestMapper.class);
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(mapper, accountRepository);
    }

    @Test
    void testCreateAccount() {

        CreateAccountRequest request = new CreateAccountRequest(
                "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                "CHECKING",
                "USD"
        );

        Account savingAccount = new Account(
                "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                "ACC-123456789",
                "CHECKING"
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

        when(mapper.fromRequest(request)).thenReturn(savingAccount);

        when(accountRepository.save(savingAccount))
                .thenReturn(Mono.just(expectedAccount));

        Mono<Account> result = accountService.createAccount(request);

        StepVerifier.create(result)
                .expectNext(expectedAccount)
                .verifyComplete();
    }

    @Test
    void testFindAll() {
        List<Account> accounts = List.of(
                createAccount("1", "1", "1", "1", "1", new BigDecimal("1"), "1", LocalDateTime.now(), LocalDateTime.now()),
                createAccount("2", "2", "2", "2", "2", new BigDecimal("2"), "2", LocalDateTime.now(), LocalDateTime.now()),
                createAccount("3", "3", "3", "3", "3", new BigDecimal("3"), "3", LocalDateTime.now(), LocalDateTime.now())
                );

        when(accountRepository.findAll()).thenReturn(Flux.fromIterable(accounts));

        Flux<Account> result = accountService.findAll();

        StepVerifier.create(result).expectNext(accounts.get(0), accounts.get(1), accounts.get(2)).verifyComplete();

    }

    private Account createAccount(final String id, final String userId, final String accountNumber,
                                  final String accountType, final String currency, final BigDecimal balance,
                                  final String status, final LocalDateTime createdAt, final LocalDateTime updatedAt) {

        AccountBuilder builder = AccountBuilder.anAccount();
        builder.withId(id);
        builder.withUserId(userId);
        builder.withAccountNumber(accountNumber);
        builder.withAccountType(accountType);
        builder.withCreatedAt(createdAt);
        builder.withUpdatedAt(updatedAt);
        builder.withCurrency(currency);
        builder.withBalance(new BigDecimal(balance.toString()));
        builder.withStatus(status);
        return builder.build();
    }
}
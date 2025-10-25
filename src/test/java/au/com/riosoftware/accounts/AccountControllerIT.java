package au.com.riosoftware.accounts;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.User;
import au.com.riosoftware.accounts.repository.AccountRepository;
import au.com.riosoftware.accounts.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class AccountControllerIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                String.format("r2dbc:postgresql://%s:%d/%s",
                        postgres.getHost(),
                        postgres.getFirstMappedPort(),
                        postgres.getDatabaseName()));
        registry.add("spring.r2dbc.username", postgres::getUsername);
        registry.add("spring.r2dbc.password", postgres::getPassword);

        registry.add("spring.flyway.url", postgres::getJdbcUrl);
        registry.add("spring.flyway.user", postgres::getUsername);
        registry.add("spring.flyway.password", postgres::getPassword);
    }

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll().block();
        userRepository.deleteAll().block();
    }

    private String createTestUser() {
        User user = new User("test-" + UUID.randomUUID() + "@example.com",
                "testuser-" + UUID.randomUUID(),
        "$2a$10$dummyHashForTesting",
        "Test",
        "User");

        User savedUser = userRepository.save(user).block();
        return savedUser.getId();
    }

    @Test
    void createAccount_shouldCreateNewAccount() {
        String userId = createTestUser();

        CreateAccountRequest request = new CreateAccountRequest(
                userId,
                "CHECKING",
                "USD"
        );

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.userId").isEqualTo(userId)
                .jsonPath("$.accountNumber").isNotEmpty()
                .jsonPath("$.accountType").isEqualTo("CHECKING")
                .jsonPath("$.currency").isEqualTo("USD")
                .jsonPath("$.status").isEqualTo("ACTIVE");
//                .jsonPath("$.balance").isEqualTo(0.0)
//                .jsonPath("$.createdAt").isNotEmpty()
//                .jsonPath("$.updatedAt").isNotEmpty();

        accountRepository.findAll()
                .as(StepVerifier::create)
                .assertNext(account -> {
                    assertThat(account.getId()).isNotNull();
                    assertThat(account.getUserId()).isEqualTo(userId);
                    assertThat(account.getAccountNumber()).isNotNull();
                    assertThat(account.getAccountType()).isEqualTo("CHECKING");
                    assertThat(account.getCurrency()).isEqualTo("USD");
                    assertThat(account.getStatus()).isEqualTo("ACTIVE");
                    assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
                    assertThat(account.getCreatedAt()).isNotNull();
                    assertThat(account.getUpdatedAt()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    void createAccount_withMultipleAccounts_shouldCreateAll() {
        String userId = createTestUser();

        CreateAccountRequest checkingRequest = new CreateAccountRequest(userId, "CHECKING", "USD");
        CreateAccountRequest savingsRequest = new CreateAccountRequest(userId, "SAVINGS", "EUR");

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(checkingRequest)
                .exchange()
                .expectStatus().isCreated();

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(savingsRequest)
                .exchange()
                .expectStatus().isCreated();

        accountRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void createAccount_withInvalidData_shouldReturnBadRequest() {
        CreateAccountRequest invalidRequest = new CreateAccountRequest(
                null,  // userId is required
                "",    // accountType is required
                ""     // currency is required
        );

        // When & Then
        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createAccount_withInvalidAccountType_shouldReturnBadRequest() {
        CreateAccountRequest request = new CreateAccountRequest(
                UUID.randomUUID().toString(),
                "INVALID_TYPE",  // Not in the allowed types
                "USD"
        );

        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createAccount_withInvalidCurrency_shouldReturnBadRequest() {
        CreateAccountRequest request = new CreateAccountRequest(
                UUID.randomUUID().toString(),
                "CHECKING",
                "INVALID_CURRENCY"  // Not in the allowed currencies
        );

        // When & Then
        webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getAllAccounts_shouldReturnAllAccounts() {
        String userId = createTestUser();
        CreateAccountRequest request1 = new CreateAccountRequest(userId, "CHECKING", "USD");
        CreateAccountRequest request2 = new CreateAccountRequest(userId, "SAVINGS", "EUR");

        webTestClient.post().uri("/accounts")
                .bodyValue(request1).exchange().expectStatus().isCreated();
        webTestClient.post().uri("/accounts")
                .bodyValue(request2).exchange().expectStatus().isCreated();

        webTestClient.get()
                .uri("/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Object.class)
                .hasSize(2);
    }

    @Test
    void getAccountById_shouldReturnAccount() {
        String userId = createTestUser();
        CreateAccountRequest request = new CreateAccountRequest(userId, "CHECKING", "USD");

        webTestClient.post()
                .uri("/accounts")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();

        String accountId = accountRepository.findAll().blockFirst().getId();

        webTestClient.get()
                .uri("/accounts/{id}", accountId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(accountId)
                .jsonPath("$.userId").isEqualTo(userId)
                .jsonPath("$.accountType").isEqualTo("CHECKING");
    }

    @Test
    void getAccountsByUserId_shouldReturnUserAccounts() {
        String userId = createTestUser();
        CreateAccountRequest request1 = new CreateAccountRequest(userId, "CHECKING", "USD");
        CreateAccountRequest request2 = new CreateAccountRequest(userId, "SAVINGS", "EUR");

        webTestClient.post().uri("/accounts").bodyValue(request1).exchange();
        webTestClient.post().uri("/accounts").bodyValue(request2).exchange();

        // When & Then
        webTestClient.get()
                .uri("/accounts/user/{userId}", userId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Object.class)
                .hasSize(2);
    }
}
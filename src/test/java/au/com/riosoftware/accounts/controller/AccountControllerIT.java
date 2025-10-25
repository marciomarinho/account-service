package au.com.riosoftware.accounts.controller;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import au.com.riosoftware.accounts.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AccountControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AccountRepository accountRepository;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/accounts";
        
        // Clean up before each test
        accountRepository.deleteAll().block();
    }

    @Test
    void createAccount_shouldCreateNewAccount() {
        // Given
        String userId = UUID.randomUUID().toString();
        CreateAccountRequest request = new CreateAccountRequest(
                userId,
                "CHECKING",
                "USD"
        );

        // When & Then
        webTestClient.post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.userId").isEqualTo(userId)
                .jsonPath("$.accountType").isEqualTo("CHECKING")
                .jsonPath("$.currency").isEqualTo("USD")
                .jsonPath("$.status").isEqualTo("ACTIVE")
                .jsonPath("$.balance").isEqualTo(0.0);

        // Verify the account was saved in the database
        accountRepository.findAll()
                .as(StepVerifier::create)
                .assertNext(account -> {
                    assertThat(account.getUserId()).isEqualTo(userId);
                    assertThat(account.getAccountType()).isEqualTo("CHECKING");
                    assertThat(account.getCurrency()).isEqualTo("USD");
                    assertThat(account.getStatus()).isEqualTo("ACTIVE");
                    assertThat(account.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
                })
                .verifyComplete();
    }

    @Test
    void createAccount_withInvalidData_shouldReturnBadRequest() {
        // Given - invalid request with missing required fields
        CreateAccountRequest invalidRequest = new CreateAccountRequest(
                null,  // userId is required
                "",     // accountType is required
                ""      // currency is required
        );

        // When & Then
        webTestClient.post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.errors").isArray()
                .jsonPath("$.errors.length()").isEqualTo(3) // Expecting 3 validation errors
                .jsonPath("$.errors[0]").isNotEmpty()
                .jsonPath("$.errors[1]").isNotEmpty()
                .jsonPath("$.errors[2]").isNotEmpty();
    }

    @Test
    void createAccount_withInvalidCurrency_shouldReturnBadRequest() {
        // Given - invalid currency
        CreateAccountRequest request = new CreateAccountRequest(
                UUID.randomUUID().toString(),
                "CHECKING",
                "INVALID_CURRENCY"  // Not in the allowed currencies
        );

        // When & Then
        webTestClient.post()
                .uri(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.errors[0]").value(error -> 
                    error.toString().contains("Currency must be USD, EUR, GBP, AUD, CAD, or JPY")
                );
    }
}

package au.com.riosoftware.accounts.service;

import au.com.riosoftware.accounts.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class AccountServiceTest {

    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
    }

    @Test
    void testCreateAccount() {

    }

    @Test
    void testFromRequest() {
    }

    @Test
    void testFindAll() {
    }
}
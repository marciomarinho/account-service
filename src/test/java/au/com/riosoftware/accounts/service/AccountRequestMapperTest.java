package au.com.riosoftware.accounts.service;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRequestMapperTest {

    private AccountRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AccountRequestMapper();
    }

    @Test
    void testFromRequest() {
        CreateAccountRequest req = new CreateAccountRequest(
                "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                "CHECKING",
                "USD"
        );
        Account result = mapper.fromRequest(req);
        assertEquals(req.userId(), result.getUserId());
        assertEquals(req.accountType(), result.getAccountType());
        assertEquals(req.currency(), result.getCurrency());
    }

}
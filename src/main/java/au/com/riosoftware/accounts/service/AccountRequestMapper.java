package au.com.riosoftware.accounts.service;

import au.com.riosoftware.accounts.controller.model.CreateAccountRequest;
import au.com.riosoftware.accounts.model.Account;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountRequestMapper {

    public Account fromRequest(final CreateAccountRequest req) {
        return new Account(req.userId(), UUID.randomUUID().toString(), req.accountType());
    }

}

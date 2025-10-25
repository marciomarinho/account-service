package au.com.riosoftware.accounts.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class AccountBuilder {
    private String id;
    private String userId;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private AccountBuilder() {
    }

    public static AccountBuilder anAccount() {
        return new AccountBuilder();
    }

    public AccountBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public AccountBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public AccountBuilder withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountBuilder withAccountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public AccountBuilder withBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public AccountBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public AccountBuilder withStatus(String status) {
        this.status = status;
        return this;
    }

    public AccountBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public AccountBuilder withUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Account build() {
        Account account = new Account();
        account.setId(id);
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBalance(balance);
        account.setCurrency(currency);
        account.setStatus(status);
        account.setCreatedAt(createdAt);
        account.setUpdatedAt(updatedAt);
        return account;
    }
}

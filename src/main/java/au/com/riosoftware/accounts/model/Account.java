package au.com.riosoftware.accounts.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("accounts")
public class Account {

    @Id
    private String id;

    @Column("user_id")
    private String userId;

    @Column("account_number")
    private String accountNumber;

    @Column("account_type")
    private String accountType; // CHECKING, SAVINGS, etc. Could be an enum.

    @Column("balance")
    private BigDecimal balance;

    @Column("currency")
    private String currency;

    @Column("status")
    private String status; // ACTIVE, SUSPENDED, CLOSED

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;

    public Account() {
        this.balance = new BigDecimal("0.0");
        this.currency = "USD";
        this.status = "ACTIVE";
    }

    public Account(final String userId, final String accountNumber, final String accountType) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = new BigDecimal("0.0");
        this.currency = "USD";
        this.status = "ACTIVE";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
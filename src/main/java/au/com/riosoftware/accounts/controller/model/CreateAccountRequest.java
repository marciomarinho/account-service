package au.com.riosoftware.accounts.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Request DTO for creating a new account.
 * Only requires the essential fields - system handles the rest.
 */
public record CreateAccountRequest(

        @NotNull(message = "User ID is required")
        String userId,

        @NotBlank(message = "Account type is required")
        @Pattern(regexp = "CHECKING|SAVINGS|BUSINESS|INVESTMENT",
                message = "Account type must be CHECKING, SAVINGS, BUSINESS, or INVESTMENT")
        String accountType,

        @NotBlank(message = "Currency is required")
        @Pattern(regexp = "USD|EUR|GBP|AUD|CAD|JPY",
                message = "Currency must be USD, EUR, GBP, AUD, CAD, or JPY")
        String currency
        
) {}
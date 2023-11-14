package com.pichincha.pichinchaapi.dto;

import com.pichincha.pichinchaapi.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    @NotBlank
    private String accountNumber;
    @NotBlank
    private AccountType accountType;
    @NotNull
    private BigDecimal initialBalance;
}

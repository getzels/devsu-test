package com.pichincha.pichinchaapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pichincha.pichinchaapi.enums.TransactionType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionDTO {
    private Long id;
    private Date date;
    @NotNull
    private Long accountId;
    @NotNull
    private TransactionType type;
    @NotNull
    private BigDecimal amount;

    @AssertTrue
    @JsonIgnore
    public boolean isValid() {
        if (amount.compareTo(BigDecimal.ZERO) < 0
                && !TransactionType.DEBIT.equals(type)) {
            return false;
        }

        if (amount.compareTo(BigDecimal.ZERO) > 0
                && !TransactionType.CREDIT.equals(type)) {
            return false;
        }

        return true;
    }
}

package com.pichincha.pichinchaapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class StatementOfAccountDTO {

    private Long clientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<AccountDetails> accounts;
    private BigDecimal totalDebits;
    private BigDecimal totalCredits;

    @Data
    public class AccountDetails {
        private Long accountId;
        private BigDecimal balance;
    }
}



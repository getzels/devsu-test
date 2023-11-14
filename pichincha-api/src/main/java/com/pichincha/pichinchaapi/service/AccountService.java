package com.pichincha.pichinchaapi.service;

import com.pichincha.pichinchaapi.dto.StatementOfAccountDTO;
import com.pichincha.pichinchaapi.entity.Account;
import com.pichincha.pichinchaapi.enums.TransactionType;
import com.pichincha.pichinchaapi.exception.CustomAPIException;
import com.pichincha.pichinchaapi.repository.AccountRepository;
import com.pichincha.pichinchaapi.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.pichincha.pichinchaapi.enums.TransactionType.CREDIT;
import static com.pichincha.pichinchaapi.enums.TransactionType.DEBIT;

@Service
@AllArgsConstructor
public class AccountService extends BaseService<Account, Long> {

    private final AccountRepository repository;
    private final TransactionRepository transactionRepository;

    @Override
    protected JpaRepository repository()
    {
        return repository;
    }

    public StatementOfAccountDTO getStatementOfAccount(Long clientId, LocalDate startDate, LocalDate endDate) {
        StatementOfAccountDTO dto = new StatementOfAccountDTO();
        dto.setClientId(clientId);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);

        dto.setTotalDebits(transactionRepository.getTotalDebitsForClient(clientId, startDate, endDate));
        dto.setTotalCredits(transactionRepository.getTotalCreditsForClient(clientId, startDate, endDate));
        dto.setAccounts(transactionRepository.getAccountDetailsForClient(clientId, endDate));

        return dto;
    }

    public BigDecimal updateAccountBalance(Long accountId, TransactionType transactionType, BigDecimal amount) {
        Account account = findById(accountId).orElseThrow(EntityNotFoundException::new);
        Account updatedAccount = new Account();
        BeanUtils.copyProperties(account, updatedAccount);
        BigDecimal balanceAfterUpdate = account.getInitialBalance();

        if (transactionType.equals(DEBIT)) {
            balanceAfterUpdate.subtract(amount);
        } else if (transactionType.equals(CREDIT)) {
            balanceAfterUpdate.add(amount);
        }
        updatedAccount.setInitialBalance(balanceAfterUpdate);
        return save(updatedAccount).getInitialBalance();
    }
}

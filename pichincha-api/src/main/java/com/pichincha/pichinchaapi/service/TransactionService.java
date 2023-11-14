package com.pichincha.pichinchaapi.service;

import com.pichincha.pichinchaapi.dto.TransactionDTO;
import com.pichincha.pichinchaapi.entity.Account;
import com.pichincha.pichinchaapi.entity.Transaction;
import com.pichincha.pichinchaapi.enums.TransactionType;
import com.pichincha.pichinchaapi.exception.CustomAPIException;
import com.pichincha.pichinchaapi.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService extends BaseService<Transaction, Long> {

    private final static int DAILY_WITHDRAWAL_LIMIT = 1000;

    private final TransactionRepository repository;
    private final AccountService accountService;

    @Override
    protected JpaRepository<Transaction, Long> repository() {
        return repository;
    }

    public Optional<Transaction> updateTransaction(Long id, TransactionDTO transactionDTO) {
        return findById(id)
                .map(transaction -> {
                    transaction.setDate(transactionDTO.getDate());
                    transaction.setAccount(accountService
                            .findById(transactionDTO.getAccountId())
                            .orElseThrow(CustomAPIException::new));
                    transaction.setType(transactionDTO.getType());
                    transaction.setAmount(transactionDTO.getAmount());
                    return save(transaction);
                });
    }

    @Override
    public Transaction save(Transaction transaction) {
        Account account = accountService.findById(transaction.getAccount().getId())
                .orElseThrow(() -> new CustomAPIException("Account not found"));

        if(TransactionType.DEBIT.equals(transaction.getType())
                && account.getInitialBalance().compareTo(transaction.getAmount()) < 0) {
                throw new CustomAPIException("Saldo no disponible");
        }

        if (TransactionType.DEBIT.equals(transaction.getType())) {
            List<Transaction> todayTransactions = repository
                    .getTransactionsByAccount_IdAndTypeAndDate(account.getId(), transaction.getType(), new Date());

            BigDecimal dailyWithdrawal = todayTransactions.stream().map(Transaction::getAmount)
                    .collect(Collectors.toList())
                    .stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (BigDecimal.valueOf(DAILY_WITHDRAWAL_LIMIT).compareTo(dailyWithdrawal) < 0) {
                throw new CustomAPIException("Cupo diario Excedido");
            }
        }
        transaction.setAccount(account);
        transaction.setBalanceAfterTransaction(
                accountService.updateAccountBalance(account.getId(),transaction.getType(),transaction.getAmount()));
        return super.save(transaction);
    }
}

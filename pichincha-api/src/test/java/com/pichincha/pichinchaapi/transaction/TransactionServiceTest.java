package com.pichincha.pichinchaapi.transaction;

import com.pichincha.pichinchaapi.entity.Account;
import com.pichincha.pichinchaapi.entity.Client;
import com.pichincha.pichinchaapi.entity.Transaction;
import com.pichincha.pichinchaapi.enums.AccountType;
import com.pichincha.pichinchaapi.enums.Gender;
import com.pichincha.pichinchaapi.enums.TransactionType;
import com.pichincha.pichinchaapi.exception.CustomAPIException;
import com.pichincha.pichinchaapi.repository.TransactionRepository;
import com.pichincha.pichinchaapi.service.AccountService;
import com.pichincha.pichinchaapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;

    private static final BigDecimal DAILY_WITHDRAWAL_LIMIT = BigDecimal.valueOf(500);

    @Test
    public void whenSaveTransaction_thenSuccess() {
        // Setting up the scenario: Account found and has sufficient balance for debit
        Account account = new Account();
        account.setId(1L);
        account.setInitialBalance(BigDecimal.valueOf(1000));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(TransactionType.CREDIT); // CREDIT or DEBIT as per your scenario

        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction savedTransaction = transactionService.save(transaction);

        assertNotNull(savedTransaction);
        assertEquals(savedTransaction.getAccount().getId(), account.getId());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void whenAccountNotFound_thenThrowException() {
        Transaction transaction = new Transaction();
        transaction.setAccount(new Account());
        transaction.getAccount().setId(2L);

        when(accountService.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomAPIException.class, () -> {
            transactionService.save(transaction);
        });
    }

    @Test
    public void whenDebitWithInsufficientBalance_thenThrowException() {
        Account account = new Account();
        account.setId(1L);
        account.setInitialBalance(BigDecimal.ZERO);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(BigDecimal.valueOf(999));
        transaction.setType(TransactionType.DEBIT);

        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));

        assertThrows(CustomAPIException.class, () -> {
            transactionService.save(transaction);
        });
    }

    @Test
    public void whenDailyWithdrawalLimitExceeded_thenThrowException() {
        Account account = new Account();
        account.setId(1L);
        account.setInitialBalance(DAILY_WITHDRAWAL_LIMIT);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType(TransactionType.DEBIT);
        transaction.setAmount(BigDecimal.valueOf(600));

        List<Transaction> existingTransactions = Arrays.asList(
                createTransaction(BigDecimal.valueOf(300), 1L),
                createTransaction(BigDecimal.valueOf(200), 2L)
        );

        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));
//        when(transactionRepository.getTransactionsByAccount_IdAndTypeAndDate(
//                eq(account.getId()), eq(TransactionType.DEBIT), any(Date.class)))
//                .thenReturn(existingTransactions);

        assertThrows(CustomAPIException.class, () -> {
            transactionService.save(transaction);
        });
    }

    private Transaction createTransaction(BigDecimal amount, Long id) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEBIT);
        transaction.setAccount(createAccount());
        return transaction;
    }

    private Account createAccount(){
        return Account.builder()
                .accountType(AccountType.SAVINGS)
                .accountNumber("CT99999")
                .initialBalance(BigDecimal.TEN)
                .status("ACTIVE")
                .client(createClient())
                .build();
    }

    private Client createClient() {
        return Client.builder()
                .clientId("CT123")
                .name("Carlos")
                .gender(Gender.MALE)
                .identification("999999999")
                .active(Boolean.TRUE)
                .build();
    }
}


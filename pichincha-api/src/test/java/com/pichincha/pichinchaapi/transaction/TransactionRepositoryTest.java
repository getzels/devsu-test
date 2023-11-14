package com.pichincha.pichinchaapi.transaction;

import com.pichincha.pichinchaapi.entity.Account;
import com.pichincha.pichinchaapi.entity.Client;
import com.pichincha.pichinchaapi.entity.Transaction;
import com.pichincha.pichinchaapi.enums.AccountType;
import com.pichincha.pichinchaapi.enums.Gender;
import com.pichincha.pichinchaapi.enums.TransactionType;
import com.pichincha.pichinchaapi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
public class TransactionRepositoryTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testSaveTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(999.99));
        transaction.setType(TransactionType.DEBIT);
        transaction.setAccount(createAccount());
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction savedTransaction = transactionRepository.save(transaction);

        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getId()).isNotNull();
        verify(transactionRepository, times(1)).save(any(Transaction.class));
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


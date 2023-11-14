package com.pichincha.pichinchaapi.repository;

import com.pichincha.pichinchaapi.dto.StatementOfAccountDTO;
import com.pichincha.pichinchaapi.entity.Transaction;
import com.pichincha.pichinchaapi.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getTransactionsByAccount_IdAndTypeAndDate(Long accountId, TransactionType type, Date date);

    // Query to get total debits for a client in a date range
    @Query("""
            SELECT SUM(t.amount) 
              FROM Transaction t 
             WHERE t.account.client.id = :clientId 
               AND t.date BETWEEN :startDate AND :endDate 
               AND t.type = 'DEBIT'
            """)
    BigDecimal getTotalDebitsForClient(@Param("clientId") Long clientId,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);

    // Query to get total credits for a client in a date range
    @Query("""
            SELECT SUM(t.amount) 
              FROM Transaction t 
             WHERE t.account.client.id = :clientId 
               AND t.date BETWEEN :startDate AND :endDate 
               AND t.type = 'CREDIT'
            """)
    BigDecimal getTotalCreditsForClient(@Param("clientId") Long clientId,
                                        @Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate);

    // Query to get account balances for a client
    @Query("""
            SELECT a.id, SUM(t.amount)
              FROM Transaction t
              JOIN t.account a
             WHERE a.client.id = :clientId
               AND t.date <= :endDate
             GROUP BY a.id
            """)
    List<StatementOfAccountDTO.AccountDetails> getAccountDetailsForClient(@Param("clientId") Long clientId,
                                                                          @Param("endDate") LocalDate endDate);
}

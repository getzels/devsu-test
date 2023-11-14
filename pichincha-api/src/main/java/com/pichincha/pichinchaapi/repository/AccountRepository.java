package com.pichincha.pichinchaapi.repository;

import com.pichincha.pichinchaapi.dto.StatementOfAccountDTO;
import com.pichincha.pichinchaapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
            SELECT t.account.id, SUM(t.amount) 
              FROM Transaction t 
             WHERE t.account.client.id = :clientId 
               AND t.date BETWEEN :startDate AND :endDate 
               GROUP BY t.account.id
               """)
    List<StatementOfAccountDTO.AccountDetails> findAccountDetailsByClientAndDateRange (@Param("clientId") Long clientId,
                                                                                      @Param("startDate") LocalDate startDate,
                                                                                      @Param("endDate") LocalDate endDate);
}

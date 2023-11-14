package com.pichincha.pichinchaapi.transaction;

import com.pichincha.pichinchaapi.controller.TransactionController;
import com.pichincha.pichinchaapi.entity.Account;
import com.pichincha.pichinchaapi.entity.Transaction;
import com.pichincha.pichinchaapi.enums.TransactionType;
import com.pichincha.pichinchaapi.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void whenPostRequestToTransactionsAndValidTransaction_thenCorrectResponse() throws Exception {
        String transactionJson = "{\"account\": {\"id\": 1}, \"type\": \"CREDIT\", \"amount\": 100}";

        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccount(Account.builder().id(1L).build());
        transaction.setType(TransactionType.CREDIT);
        transaction.setAmount(new BigDecimal(100));

        when(transactionService.save(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.type").value("CREDIT"))
                .andExpect(jsonPath("$.amount").value(100));
    }
}


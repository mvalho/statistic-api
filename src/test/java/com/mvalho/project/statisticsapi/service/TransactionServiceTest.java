package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.impl.TransactionServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionServiceTest {
    private TransactionService transactionService;

    @Test
    public void saveShouldRespondWithCode201WhenTheTransactionIsWithinTheLast60Seconds() {
        this.transactionService = new TransactionServiceImpl();
        Transaction transaction = new Transaction(new BigDecimal(50.0), LocalDateTime.now().minusSeconds(10));
        int expectedCode = 201;

        assertThat(this.transactionService.save(transaction)).isEqualTo(expectedCode);
    }
}

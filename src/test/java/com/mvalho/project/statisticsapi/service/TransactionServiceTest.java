package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.entity.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void saveShouldRespondWithCode201WhenTheTransactionIsWithinTheLast60Seconds() {
        Transaction transaction = new Transaction(new BigDecimal(50.0), LocalDateTime.now().minusSeconds(10));
        int expectedCode = 201;

        assertThat(this.transactionService.save(transaction)).isEqualTo(expectedCode);
    }

    @Test
    public void saveShouldRespondWithCode204WhenTheTransactionIsOutsideTheLast60Seconds() {
        Transaction transaction = new Transaction(new BigDecimal(12.3), LocalDateTime.now().minusSeconds(61));
        int expectedCode = 204;

        assertThat(this.transactionService.save(transaction)).isEqualTo(expectedCode);
    }
}

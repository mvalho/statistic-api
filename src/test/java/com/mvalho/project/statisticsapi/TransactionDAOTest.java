package com.mvalho.project.statisticsapi;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionDAOTest {
    @Autowired
    private TransactionDAO transactionDAO;

    @Test
    public void shouldStoreTransactionWhenNewTransactionIsUsed() {
        Transaction transaction = new Transaction(new BigDecimal(12.3), LocalDateTime.ofInstant(Instant.ofEpochSecond(1478192204000L), ZoneId.systemDefault()));
        Transaction expected = new Transaction(new BigDecimal(12.3), LocalDateTime.ofInstant(Instant.ofEpochSecond(1478192204000L), ZoneId.systemDefault()));

        assertThat(this.transactionDAO.add(transaction)).isEqualTo(expected);
    }
}

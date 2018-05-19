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
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void shouldRetrieveAllTransactionWhenTheCreatedDateIsNotGreaterThan60Seconds() {
        long timestampNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampNow), ZoneId.systemDefault());

        Transaction transaction1 = new Transaction(new BigDecimal(12.3), localDateTime.minusSeconds(10));
        Transaction transaction2 = new Transaction(new BigDecimal(20.0), localDateTime);
        Transaction transaction3 = new Transaction(new BigDecimal(15.5), localDateTime.minusSeconds(20));
        Transaction transaction4 = new Transaction(new BigDecimal(56.78), localDateTime.minusSeconds(70));
        Transaction transaction5 = new Transaction(new BigDecimal(45.6), localDateTime.minusSeconds(80));
        Transaction transaction6 = new Transaction(new BigDecimal(98.7), localDateTime.minusSeconds(61));

        this.transactionDAO.add(transaction1);
        this.transactionDAO.add(transaction2);
        this.transactionDAO.add(transaction3);
        this.transactionDAO.add(transaction4);
        this.transactionDAO.add(transaction5);
        this.transactionDAO.add(transaction6);

        List<Transaction> expected = Arrays.asList(
                new Transaction(new BigDecimal(12.3), localDateTime.minusSeconds(10)),
                new Transaction(new BigDecimal(20.0), localDateTime),
                new Transaction(new BigDecimal(15.5), localDateTime.minusSeconds(20)));

        assertThat(this.transactionDAO.getLastTransactions(localDateTime))
                .hasSize(3)
                .hasSameElementsAs(expected);
    }
}

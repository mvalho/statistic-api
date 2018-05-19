package com.mvalho.project.statisticsapi.dao;

import com.mvalho.project.statisticsapi.entity.Transaction;
import org.junit.Before;
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
    private LocalDateTime localDateTime;

    @Autowired
    private TransactionDAO transactionDAO;

    @Before
    public void setUp() {
        this.transactionDAO.resetTransactionList();
        long timestampNow = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        this.localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampNow), ZoneId.systemDefault());
    }

    @Test
    public void addShouldStoreTransactionWhenNewTransactionIsUsed() {
        Transaction transaction = new Transaction(new BigDecimal(12.3), LocalDateTime.ofInstant(Instant.ofEpochSecond(1478192204000L), ZoneId.systemDefault()));
        Transaction expected = new Transaction(new BigDecimal(12.3), LocalDateTime.ofInstant(Instant.ofEpochSecond(1478192204000L), ZoneId.systemDefault()));

        assertThat(this.transactionDAO.add(transaction)).isEqualTo(expected);
    }

    @Test
    public void getLastTransactionsShouldRetrieveAllTransactionWhenTheCreatedDateIsNotGreaterThan60Seconds() {
        Transaction transaction1 = new Transaction(new BigDecimal(12.3), this.localDateTime.minusSeconds(10));
        Transaction transaction2 = new Transaction(new BigDecimal(20.0), this.localDateTime);
        Transaction transaction3 = new Transaction(new BigDecimal(15.5), this.localDateTime.minusSeconds(20));
        Transaction transaction4 = new Transaction(new BigDecimal(56.78), this.localDateTime.minusSeconds(70));
        Transaction transaction5 = new Transaction(new BigDecimal(45.6), this.localDateTime.minusSeconds(80));
        Transaction transaction6 = new Transaction(new BigDecimal(98.7), this.localDateTime.minusSeconds(61));

        this.transactionDAO.add(transaction1);
        this.transactionDAO.add(transaction2);
        this.transactionDAO.add(transaction3);
        this.transactionDAO.add(transaction4);
        this.transactionDAO.add(transaction5);
        this.transactionDAO.add(transaction6);

        List<Transaction> expected = Arrays.asList(
                new Transaction(new BigDecimal(12.3), this.localDateTime.minusSeconds(10)),
                new Transaction(new BigDecimal(20.0), this.localDateTime),
                new Transaction(new BigDecimal(15.5), this.localDateTime.minusSeconds(20)));

        assertThat(this.transactionDAO.getLastTransactions(this.localDateTime))
                .hasSize(3)
                .hasSameElementsAs(expected);
    }

    @Test
    public void getLastTransactionsShouldNotRetrieveTransactionWhenNoTransactionWasAdded() {
        assertThat(this.transactionDAO.getLastTransactions(this.localDateTime)).hasSize(0).isEmpty();
    }
}

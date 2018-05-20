package com.mvalho.project.statisticsapi.dao;

import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.util.TransactionBuilder;
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
        Transaction transaction = TransactionBuilder.create().withAmount(new BigDecimal(12.3)).withCreatedDate(1478192204000L).build();
        Transaction expected = TransactionBuilder.create().withAmount(new BigDecimal(12.3)).withCreatedDate(1478192204000L).build();

        assertThat(this.transactionDAO.add(transaction)).isEqualTo(expected);
    }

    @Test
    public void getLastTransactionsShouldRetrieveAllTransactionWhenTheCreatedDateIsNotGreaterThan60Seconds() {
        Transaction transaction1 = TransactionBuilder.create().withAmount(new BigDecimal(12.3)).withCreatedDate(this.localDateTime.minusSeconds(10)).build();
        Transaction transaction2 = TransactionBuilder.create().withAmount(new BigDecimal(20.0)).withCreatedDate(this.localDateTime).build();
        Transaction transaction3 = TransactionBuilder.create().withAmount(new BigDecimal(15.5)).withCreatedDate(this.localDateTime.minusSeconds(20)).build();
        Transaction transaction4 = TransactionBuilder.create().withAmount(new BigDecimal(56.78)).withCreatedDate(this.localDateTime.minusSeconds(70)).build();
        Transaction transaction5 = TransactionBuilder.create().withAmount(new BigDecimal(45.6)).withCreatedDate(this.localDateTime.minusSeconds(80)).build();
        Transaction transaction6 = TransactionBuilder.create().withAmount(new BigDecimal(98.7)).withCreatedDate(this.localDateTime.minusSeconds(61)).build();

        this.transactionDAO.add(transaction1);
        this.transactionDAO.add(transaction2);
        this.transactionDAO.add(transaction3);
        this.transactionDAO.add(transaction4);
        this.transactionDAO.add(transaction5);
        this.transactionDAO.add(transaction6);

        List<Transaction> expected = Arrays.asList(
                TransactionBuilder.create().withAmount(new BigDecimal(12.3)).withCreatedDate(this.localDateTime.minusSeconds(10)).build(),
                TransactionBuilder.create().withAmount(new BigDecimal(20.0)).withCreatedDate(this.localDateTime).build(),
                TransactionBuilder.create().withAmount(new BigDecimal(15.5)).withCreatedDate(this.localDateTime.minusSeconds(20)).build());

        assertThat(this.transactionDAO.getLastTransactions(this.localDateTime))
                .hasSize(3)
                .hasSameElementsAs(expected);
    }

    @Test
    public void getLastTransactionsShouldNotRetrieveTransactionWhenNoTransactionWasAdded() {
        assertThat(this.transactionDAO.getLastTransactions(this.localDateTime)).hasSize(0).isEmpty();
    }
}

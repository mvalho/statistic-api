package com.mvalho.project.statisticsapi;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.dao.impl.TransactionDAOImpl;
import com.mvalho.project.statisticsapi.entity.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionDAOTest {
    private TransactionDAO transactionDAO;

    @Test
    public void shouldStoreTransactionWhenNewTransactionIsUsed() {
        this.transactionDAO = new TransactionDAOImpl();
        Transaction transaction = new Transaction(new BigDecimal(12.3), LocalDateTime.ofInstant(Instant.ofEpochSecond(1478192204000L), ZoneId.systemDefault()));
        Transaction expected = new Transaction(new BigDecimal(12.3), LocalDateTime.ofInstant(Instant.ofEpochSecond(1478192204000L), ZoneId.systemDefault()));

        assertThat(this.transactionDAO.add(transaction)).isEqualTo(expected);
    }
}

package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import com.mvalho.project.statisticsapi.service.impl.StatisticServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticServiceTest {
    @Autowired
    private TransactionDAO transactionDAO;

    private StatisticService statisticService;

    @Autowired
    private TransactionRepository transactionRepository;
    private LocalDateTime now;
    private List<Transaction> lastTransactions;

    @Before
    public void setUp() {
        this.statisticService = new StatisticServiceImpl(this.transactionRepository);
        this.transactionDAO.resetTransactionList();

        now = LocalDateTime.now();
        Transaction transaction1 = new Transaction(new BigDecimal(10.28, MathContext.DECIMAL32), now.minusSeconds(10));
        Transaction transaction2 = new Transaction(new BigDecimal(123.45, MathContext.DECIMAL32), now.minusSeconds(70));
        Transaction transaction3 = new Transaction(new BigDecimal(50.25, MathContext.DECIMAL32), now.minusSeconds(90));
        Transaction transaction4 = new Transaction(new BigDecimal(58.24, MathContext.DECIMAL32), now.minusSeconds(2));
        Transaction transaction5 = new Transaction(new BigDecimal(98.12, MathContext.DECIMAL32), now.minusSeconds(35));
        Transaction transaction6 = new Transaction(new BigDecimal(46.32, MathContext.DECIMAL32), now.minusSeconds(61));
        Transaction transaction7 = new Transaction(new BigDecimal(75.46, MathContext.DECIMAL32), now.minusSeconds(90));

        this.transactionDAO.add(transaction1);
        this.transactionDAO.add(transaction2);
        this.transactionDAO.add(transaction3);
        this.transactionDAO.add(transaction4);
        this.transactionDAO.add(transaction5);
        this.transactionDAO.add(transaction6);
        this.transactionDAO.add(transaction7);

        this.lastTransactions = this.transactionDAO.getLastTransactions(now);

    }

    @Test
    public void sumShouldCalculateTheSumOfAllAmountsFromTheTransactionWithinTheLast60Seconds() {
        double expected = 166.64;

        assertThat(this.statisticService.sum(lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void sumShouldReturnZeroWhenTheListIsEmpty() {
        this.lastTransactions.clear();
        double expected = 0.0;

        assertThat(this.statisticService.sum(this.lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void averageShouldCalculateTheAverageValueOfAllAmountFromTheTransactionsWithinTheLast60Seconds() {
        double expected = 55.54666666666666;

        assertThat(this.statisticService.average(lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void averageShouldReturnZeroWhenTheListIsEmpty() {
        this.lastTransactions.clear();
        double expected = 0.0;

        assertThat(this.statisticService.average(lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void maxShouldReturnTheHighestAmountFromTheTransactionsWithinTheLast60Seconds() {
        double expected = 98.12;

        assertThat(this.statisticService.max(this.lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void maxShouldReturnZeroWhenTheListIsEmpty() {
        this.lastTransactions.clear();
        double expected = 0.0;

        assertThat(this.statisticService.max(this.lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void minShouldReturnTheLowestAmountFromTheTransactionsWithinTheLast60Second() {
        double expected = 10.28;

        assertThat(this.statisticService.min(this.lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void minShouldReturnZeroWhenTheListIsEmpty() {
        this.lastTransactions.clear();
        double expected = 0.0;

        assertThat(this.statisticService.min(this.lastTransactions)).isEqualTo(expected);
    }
}

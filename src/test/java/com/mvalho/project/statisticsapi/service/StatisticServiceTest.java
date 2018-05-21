package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.dto.StatisticDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import com.mvalho.project.statisticsapi.service.impl.StatisticServiceImpl;
import com.mvalho.project.statisticsapi.util.StatisticDtoBuilder;
import com.mvalho.project.statisticsapi.util.TransactionBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

        now = LocalDateTime.now(ZoneId.of("+0"));
        Transaction transaction1 = TransactionBuilder.create().withAmount(10.28).withCreatedDate(now.minusSeconds(10)).build();
        Transaction transaction2 = TransactionBuilder.create().withAmount(123.45).withCreatedDate(now.minusSeconds(70)).build();
        Transaction transaction3 = TransactionBuilder.create().withAmount(50.27).withCreatedDate(now.minusSeconds(90)).build();
        Transaction transaction4 = TransactionBuilder.create().withAmount(58.24).withCreatedDate(now.minusSeconds(2)).build();
        Transaction transaction5 = TransactionBuilder.create().withAmount(98.12).withCreatedDate(now.minusSeconds(35)).build();
        Transaction transaction6 = TransactionBuilder.create().withAmount(46.32).withCreatedDate(now.minusSeconds(61)).build();
        Transaction transaction7 = TransactionBuilder.create().withAmount(75.46).withCreatedDate(now.minusSeconds(90)).build();

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

        assertThat(Math.round(this.statisticService.sum(lastTransactions) * 100.0) / 100.0).isEqualTo(expected);
    }

    @Test
    public void sumShouldReturnZeroWhenTheListIsEmpty() {
        this.lastTransactions.clear();
        double expected = 0.0;

        assertThat(this.statisticService.sum(this.lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void averageShouldCalculateTheAverageValueOfAllAmountFromTheTransactionsWithinTheLast60Seconds() {
        double expected = 55.5467;

        assertThat(Math.round(this.statisticService.average(lastTransactions) * 10000.0) / 10000.0).isEqualTo(expected);
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

    @Test
    public void countShouldReturnTheTotalOfTransactionsWithingTheLast60Seconds() {
        long expected = 3L;

        assertThat(this.statisticService.count(this.lastTransactions)).isEqualTo(expected);
    }

    @Test
    public void getStatisticShouldReturnAllStatisticsWithinTheLast60Seconds() {
        StatisticDTO expected = StatisticDtoBuilder
                .create()
                .withSum(166.64)
                .withAverage(55.5467)
                .withMax(98.12)
                .withMin(10.28)
                .withCount(3L)
                .build();

        assertThat(this.statisticService.getStatistics()).isEqualToComparingFieldByField(expected);
    }
}

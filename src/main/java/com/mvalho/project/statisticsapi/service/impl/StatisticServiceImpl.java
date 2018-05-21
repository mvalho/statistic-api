package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.dto.StatisticDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import com.mvalho.project.statisticsapi.service.StatisticService;
import com.mvalho.project.statisticsapi.util.StatisticDtoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class StatisticServiceImpl implements StatisticService {

    private TransactionRepository transactionRepository;
    private List<Transaction> lastTransactions;

    @Autowired
    public StatisticServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public double sum(List<Transaction> lastTransactions) {
        return lastTransactions.stream().flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount().doubleValue())).sum();
    }

    @Override
    public double average(List<Transaction> lastTransactions) {
        return lastTransactions.stream().flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount().doubleValue())).average().orElse(0.0);
    }

    @Override
    public double max(List<Transaction> lastTransactions) {
        return lastTransactions.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).max().orElse(0.0);
    }

    @Override
    public double min(List<Transaction> lastTransactions) {
        return lastTransactions.stream().mapToDouble(transaction -> transaction.getAmount().doubleValue()).min().orElse(0.0);
    }

    @Override
    public long count(List<Transaction> lastTransactions) {
        return lastTransactions.size();
    }

    @Override
    public StatisticDTO getStatistics() {
        lastTransactions = this.transactionRepository.getLastTransactions(LocalDateTime.now(ZoneId.of("+0")));

        return createStatisticDTO();
    }

    private StatisticDTO createStatisticDTO() {
        return StatisticDtoBuilder
                .create()
                .withSum(sum(lastTransactions))
                .withAverage(average(lastTransactions))
                .withMax(max(lastTransactions))
                .withMin(min(lastTransactions))
                .withCount(count(lastTransactions))
                .build();
    }
}

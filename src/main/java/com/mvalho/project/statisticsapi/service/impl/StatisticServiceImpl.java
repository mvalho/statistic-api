package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.entity.StatisticDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import com.mvalho.project.statisticsapi.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.DoubleStream;

@Service
public class StatisticServiceImpl implements StatisticService {

    private TransactionRepository transactionRepository;

    @Autowired
    public StatisticServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public double sum(List<Transaction> lastTransactions) {
        return lastTransactions.stream().flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount())).sum();
    }

    @Override
    public double average(List<Transaction> lastTransactions) {
        return lastTransactions.stream().flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount())).average().orElse(0.0);
    }

    @Override
    public double max(List<Transaction> lastTransactions) {
        return lastTransactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0.0);
    }

    @Override
    public double min(List<Transaction> lastTransactions) {
        return lastTransactions.stream().mapToDouble(Transaction::getAmount).min().orElse(0.0);
    }

    @Override
    public long count(List<Transaction> lastTransactions) {
        return lastTransactions.size();
    }

    @Override
    public StatisticDTO getStatistics() {
        List<Transaction> lastTransactions = this.transactionRepository.getLastTransactions(LocalDateTime.now());

        StatisticDTO statisticDTO = new StatisticDTO();
        statisticDTO.setSum(this.sum(lastTransactions));
        statisticDTO.setAvg(this.average(lastTransactions));
        statisticDTO.setMax(this.max(lastTransactions));
        statisticDTO.setMin(this.min(lastTransactions));
        statisticDTO.setCount(this.count(lastTransactions));

        return statisticDTO;
    }
}

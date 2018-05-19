package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.entity.Transaction;

import java.util.List;

public interface StatisticService {
    double sum(List<Transaction> lastTransactions);

    double average(List<Transaction> lastTransactions);

    double max(List<Transaction> lastTransactions);

    double min(List<Transaction> lastTransactions);

    long count(List<Transaction> lastTransactions);
}

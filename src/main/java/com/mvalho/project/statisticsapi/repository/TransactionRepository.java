package com.mvalho.project.statisticsapi.repository;

import com.mvalho.project.statisticsapi.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository {
    Transaction add(Transaction transaction);

    List<Transaction> getLastTransactions(LocalDateTime localDateTime);
}

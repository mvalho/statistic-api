package com.mvalho.project.statisticsapi.dao;

import com.mvalho.project.statisticsapi.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionDAO {
    Transaction add(Transaction transaction);

    List<Transaction> getLastTransactions(LocalDateTime localDateTime);

    void resetTransactionList();
}

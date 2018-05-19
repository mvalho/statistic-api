package com.mvalho.project.statisticsapi.dao.impl;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionDAOImpl implements TransactionDAO {
    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public Transaction add(Transaction transaction) {
        this.transactionList.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> getLastTransactions(LocalDateTime localDateTime) {
        List<Transaction> transactionsWithin60Seconds = new ArrayList<>();
        LocalDateTime timeRange = localDateTime.minusSeconds(60);

        for (Transaction transaction : this.transactionList) {
            if (transaction.getCreated().isAfter(timeRange)) {
                transactionsWithin60Seconds.add(transaction);
            }
        }

        return transactionsWithin60Seconds;
    }
}

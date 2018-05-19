package com.mvalho.project.statisticsapi.dao.impl;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return this.transactionList.stream().filter(transaction -> transaction.getCreated().isAfter(localDateTime.minusSeconds(60))).collect(Collectors.toList());
    }

    @Override
    public void resetTransactionList() {
        this.transactionList.clear();
    }
}

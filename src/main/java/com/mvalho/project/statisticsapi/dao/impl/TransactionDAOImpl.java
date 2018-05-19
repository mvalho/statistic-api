package com.mvalho.project.statisticsapi.dao.impl;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    private List<Transaction> transactionList = new ArrayList<>();

    @Override
    public Transaction add(Transaction transaction) {
        this.transactionList.add(transaction);
        return transaction;
    }
}

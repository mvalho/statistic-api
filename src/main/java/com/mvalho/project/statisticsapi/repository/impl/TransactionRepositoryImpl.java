package com.mvalho.project.statisticsapi.repository.impl;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private TransactionDAO transactionDAO;

    @Autowired
    public TransactionRepositoryImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public Transaction add(Transaction transaction) {
        return this.transactionDAO.add(transaction);
    }

    @Override
    public List<Transaction> getLastTransactions(LocalDateTime localDateTime) {
        return this.transactionDAO.getLastTransactions(localDateTime);
    }
}

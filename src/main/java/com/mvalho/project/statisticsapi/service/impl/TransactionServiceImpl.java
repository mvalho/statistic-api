package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.dao.TransactionDAO;
import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionServiceImpl implements TransactionService {
    private TransactionDAO transactionDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public TransactionDTO save(Transaction transaction) {
        this.transactionDAO.add(transaction);

        return new TransactionDTO(getResponseCode(transaction), transaction);
    }

    private int getResponseCode(Transaction transaction) {
        LocalDateTime timeRange = LocalDateTime.now().minusSeconds(60);
        return transaction.getCreated().isAfter(timeRange) ? 201 : 204;
    }
}

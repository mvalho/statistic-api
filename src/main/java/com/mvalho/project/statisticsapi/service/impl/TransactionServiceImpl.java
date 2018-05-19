package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.TransactionService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Override
    public int save(Transaction transaction) {
        return getResponseCode(transaction);
    }

    private int getResponseCode(Transaction transaction) {
        LocalDateTime timeRange = LocalDateTime.now().minusSeconds(60);
        return transaction.getCreated().isAfter(timeRange) ? 201 : 204;
    }
}

package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.TransactionService;

import java.time.LocalDateTime;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public int save(Transaction transaction) {
        return getResponseCode(transaction);
    }

    private int getResponseCode(Transaction transaction) {
        LocalDateTime timeRange = LocalDateTime.now().minusSeconds(60);
        int responseCode = 0;
        if (transaction.getCreated().isAfter(timeRange)) {
            responseCode = 201;
        } else {
            responseCode = 204;
        }
        return responseCode;
    }
}

package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.service.TransactionService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Override
    public TransactionDTO save(Transaction transaction) {
        return new TransactionDTO(getResponseCode(transaction), transaction);
    }

    private int getResponseCode(Transaction transaction) {
        LocalDateTime timeRange = LocalDateTime.now().minusSeconds(60);
        return transaction.getCreated().isAfter(timeRange) ? 201 : 204;
    }
}

package com.mvalho.project.statisticsapi.service.impl;

import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;
import com.mvalho.project.statisticsapi.repository.TransactionRepository;
import com.mvalho.project.statisticsapi.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionDTO save(Transaction transaction) {
        this.transactionRepository.add(transaction);

        return new TransactionDTO(getResponseCode(transaction), transaction);
    }

    private int getResponseCode(Transaction transaction) {
        LocalDateTime timeRange = LocalDateTime.now().minusSeconds(60);
        return transaction.getCreated().isAfter(timeRange) ? 201 : 204;
    }
}

package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;

public interface TransactionService {
    TransactionDTO save(Transaction transaction);
}

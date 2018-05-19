package com.mvalho.project.statisticsapi.service;

import com.mvalho.project.statisticsapi.entity.Transaction;

public interface TransactionService {
    int save(Transaction transaction);
}

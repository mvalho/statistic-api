package com.mvalho.project.statisticsapi.dao;

import com.mvalho.project.statisticsapi.entity.Transaction;

public interface TransactionDAO {
    Transaction add(Transaction transaction);
}

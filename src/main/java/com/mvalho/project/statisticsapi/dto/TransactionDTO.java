package com.mvalho.project.statisticsapi.dto;

import com.mvalho.project.statisticsapi.entity.Transaction;

public class TransactionDTO {
    private Transaction transaction;
    private Integer responseCode;

    public TransactionDTO(int responseCode, Transaction transaction) {
        this.responseCode = responseCode;
        this.transaction = transaction;
    }
}

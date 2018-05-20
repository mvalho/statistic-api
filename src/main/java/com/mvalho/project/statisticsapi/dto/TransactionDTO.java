package com.mvalho.project.statisticsapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvalho.project.statisticsapi.entity.Transaction;

import java.time.ZoneOffset;

public class TransactionDTO {
    @JsonIgnore
    private Transaction transaction;

    @JsonIgnore
    private Integer responseCode;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("timestamp")
    private long timestamp;

    public TransactionDTO() {
    }

    public TransactionDTO(int responseCode, Transaction transaction) {
        this.responseCode = responseCode;
        this.transaction = transaction;
        this.amount = transaction.getAmount();
        this.timestamp = transaction.getCreated().toEpochSecond(ZoneOffset.UTC);
    }


    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Integer getResponseCode() {
        return responseCode;
    }
}

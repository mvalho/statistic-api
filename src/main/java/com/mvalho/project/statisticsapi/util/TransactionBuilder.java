package com.mvalho.project.statisticsapi.util;

import com.mvalho.project.statisticsapi.dto.TransactionDTO;
import com.mvalho.project.statisticsapi.entity.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TransactionBuilder {
    private static Transaction transaction;

    public static TransactionPropertiesBuilder create() {
        transaction = new Transaction();
        return new TransactionPropertiesBuilder();
    }

    public static class TransactionPropertiesBuilder {
        public TransactionPropertiesBuilder withAmount(double amount) {
            transaction.setAmount(BigDecimal.valueOf(amount));
            return this;
        }

        public TransactionPropertiesBuilder withAmount(BigDecimal amount) {
            transaction.setAmount(amount);
            return this;
        }

        public TransactionPropertiesBuilder withCreatedDate(LocalDateTime created) {
            transaction.setCreated(created);
            return this;
        }

        public TransactionPropertiesBuilder withCreatedDate(long timestamp) {
            LocalDateTime created = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.of("+0"));
            transaction.setCreated(created);
            return this;
        }

        public Transaction build() {
            return transaction;
        }

        public TransactionDTO buildDTO(int responseCode) {
            return new TransactionDTO(responseCode, transaction);
        }
    }
}

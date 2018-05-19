package com.mvalho.project.statisticsapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private LocalDateTime created;
    private BigDecimal amount;

    public Transaction(BigDecimal amount, LocalDateTime created) {
        this.amount = amount;
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(created, that.created) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, amount);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public double getAmount() {
        return amount.doubleValue();
    }
}

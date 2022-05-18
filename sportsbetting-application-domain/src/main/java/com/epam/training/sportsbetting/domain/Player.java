package com.epam.training.sportsbetting.domain;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.experimental.SuperBuilder;


@SuperBuilder
public class Player extends User {
    @Builder.Default
    private String name = "";
    private BigDecimal balance;
    private Currency currency;

    public String getName() {
        return name;
    }

    public synchronized BigDecimal getBalance() {
        return balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public synchronized void addBalance(BigDecimal value) {
        balance = this.balance.add(value);
    }

    public synchronized void subtractBalance(BigDecimal value) {
        balance = this.balance.subtract(value);
    }

    @Override
    public String toString() {
        return "\n> Welcome " + name + "!\n> Your balance is " + balance + " " + currency + "\n";
    }
}

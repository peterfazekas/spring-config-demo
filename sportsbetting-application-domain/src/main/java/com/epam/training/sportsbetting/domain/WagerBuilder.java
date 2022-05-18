package com.epam.training.sportsbetting.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WagerBuilder {
    private final BigDecimal amount;
    private LocalDateTime timestampCreated;
    private boolean win;
    private boolean processed;
    private Player player;
    private Outcome outcome;
    private Currency currency;

    public WagerBuilder(BigDecimal amount) {
        this.amount = amount;
        this.timestampCreated = LocalDateTime.now();
        this.win = false;
        this.processed = false;
        this.player = null;
        this.outcome = null;
        this.currency = Currency.EUR;
    }

    public WagerBuilder setTimeStamp(LocalDateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
        return this;
    }

    public WagerBuilder setWin(boolean win) {
        this.win = win;
        return this;
    }

    public WagerBuilder setProcessed(boolean processed) {
        this.processed = processed;
        return this;
    }

    public WagerBuilder setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public WagerBuilder setOutcome(Outcome outcome) {
        this.outcome = outcome;
        return this;
    }

    public WagerBuilder setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public Wager build() {
        return new Wager(this);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isProcessed() {
        return processed;
    }

    public Player getPlayer() {
        return player;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Currency getCurrency() {
        return currency;
    }
}

package com.epam.training.sportsbetting.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Wager {
    private final BigDecimal amount;
    private final LocalDateTime timestampCreated;
    private boolean win;
    private boolean processed;
    private final Player player;
    private final Outcome outcome;
    private final Currency currency;

    public Wager(WagerBuilder wagerBuilder) {
        this.amount = wagerBuilder.getAmount();
        this.timestampCreated = wagerBuilder.getTimestampCreated();
        this.win = wagerBuilder.isWin();
        this.processed = wagerBuilder.isProcessed();
        this.player = wagerBuilder.getPlayer();
        this.outcome = wagerBuilder.getOutcome();
        this.currency = wagerBuilder.getCurrency();
    }

    public Player getPlayer() {
        return player;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public String getBetDescription() {
        return outcome.getBetDescription();
    }

    public String getOutcomeDescription() {
        return outcome.getDescription();
    }

    public String getEventTitle() {
        return outcome.getEventTitle();
    }

    public BigDecimal getOutcomeOdd() {
        return outcome.getOdd();
    }

    public BigDecimal getPlayerBalance() {
        return player.getBalance();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public boolean isProcessed() {
        return processed;
    }

    public synchronized void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public boolean isWin() {
        return win;
    }

    public synchronized void setWin(boolean win) {
        this.win = win;
    }
}

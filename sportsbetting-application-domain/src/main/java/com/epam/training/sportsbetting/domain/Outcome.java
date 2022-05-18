package com.epam.training.sportsbetting.domain;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public final class Outcome {

    private final String description;
    private final BigDecimal odd;
    private boolean win;
    private final Bet bet;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private Outcome(OutcomeBuilder outcomeBuilder) {
        this.description = outcomeBuilder.description;
        this.odd = outcomeBuilder.odd;
        this.win = outcomeBuilder.win;
        this.bet = outcomeBuilder.bet;
    }

    public String getDescription() {
        return description;
    }

    public String getBetDescription() {
        return bet.getDescription();
    }

    public String getEventStartDate() {
        return bet.getEvent().getStartDate().format(formatter);
    }

    public String getEventTitle() {
        return bet.getEvent().getTitle();
    }

    public Bet getBet() {
        return bet;
    }

    public BigDecimal getOdd() {
        return odd;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public static class OutcomeBuilder {
        private final String description;
        private BigDecimal odd;
        private boolean win;
        private Bet bet;

        public OutcomeBuilder(String description) {
            this.description = description;
        }

        public OutcomeBuilder setOdd(BigDecimal odd) {
            this.odd = odd;
            return this;
        }

        public OutcomeBuilder setBet(Bet bet) {
            this.bet = bet;
            return this;
        }

        public OutcomeBuilder setWin(boolean win) {
            this.win = win;
            return this;
        }

        public Outcome build() {
            return new Outcome(this);
        }

    }

    @Override
    public String toString() {
        return ": Sport event: " + getEventTitle()
                + " (start: " + getEventStartDate()
                + "), Bet: " + getBetDescription()
                + ", Outcome: " + this.description
                + ", Actual odd: " + this.odd + ".";
    }
}

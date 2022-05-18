package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FieldInitializer {
    public static final BigDecimal PLAYER_BALANCE_START_VALUE = BigDecimal.valueOf(3456);
    public static final BigDecimal VALID_BET_VALUE = BigDecimal.valueOf(10);
    public static final BigDecimal INVALID_BET_VALUE = BigDecimal.valueOf(10000);
    public static final BigDecimal ODD = BigDecimal.valueOf(234);
    public static final String OUTCOME_DESCRIPTION = "Outcome Description";
    public static final String BET_DESCRIPTION = "Bet Description";
    public static final String EVENT_TITLE = "Event Title";
    public static final String TEST_EMAIL_COM = "test@email.com";
    public static final String TEST_PASSWORD = "testPassword";
    public static final Currency TEST_CURRENCY = Currency.EUR;
    public static final String TEST_PLAYER_NAME = "Test Player";
    public static final User VALID_USER = User.builder()
            .email(TEST_EMAIL_COM)
            .password(TEST_PASSWORD)
            .build();
    public static final User INVALID_EMAIL_USER = User.builder()
            .email("invalid@email.com")
            .password(TEST_PASSWORD)
            .build();
    public static final User INVALID_PASSWORD_USER = User.builder()
            .email(TEST_EMAIL_COM)
            .password("invalidPassword")
            .build();

    public static Wager createLoserWager(Player player) {
        return new WagerBuilder(VALID_BET_VALUE)
                .setOutcome(createOutcome(createBet()))
                .setPlayer(player)
                .build();
    }

    public static Wager createProcessedWager(Player player) {
        return new WagerBuilder(VALID_BET_VALUE)
                .setOutcome(createOutcome(createBet()))
                .setProcessed(true)
                .setWin(true)
                .setPlayer(player)
                .build();
    }

    public static Wager createWinnerWager(Player player) {
        return new WagerBuilder(VALID_BET_VALUE)
                .setOutcome(createOutcome(createBet()))
                .setWin(true)
                .setPlayer(player)
                .build();
    }

    public static Player createPlayer() {
        return Player.builder()
                .name(TEST_PLAYER_NAME)
                .email(TEST_EMAIL_COM)
                .password(TEST_PASSWORD)
                .balance(PLAYER_BALANCE_START_VALUE)
                .currency(TEST_CURRENCY)
                .build();
    }

    public static SportEvent createEvent() {
        return new FootballSportEvent(EVENT_TITLE, LocalDateTime.of(1990, 5, 10, 12, 0, 0));
    }

    public static Outcome createOutcome(Bet bet) {
        return new Outcome.OutcomeBuilder(OUTCOME_DESCRIPTION)
                .setOdd(ODD)
                .setBet(bet)
                .build();
    }

    public static Bet createBet() {
        Bet bet = new Bet();
        bet.setDescription(BET_DESCRIPTION);
        bet.setEvent(createEvent());
        return bet;
    }

    public static List<Bet> createBetList() {
        Bet bet = createBet();
        Outcome outcome1 = createOutcome(bet);
        Outcome outcome2 = createOutcome(bet);
        Outcome outcome3 = createOutcome(bet);
        List<Outcome> outcomes = new ArrayList<>();
        outcomes.add(outcome1);
        outcomes.add(outcome2);
        outcomes.add(outcome3);
        List<Bet> bets = new ArrayList<>();
        bet.setOutcomes(outcomes);
        bets.add(bet);
        return bets;
    }
}

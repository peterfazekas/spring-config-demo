package com.epam.training.sportsbetting.data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Currency;
import com.epam.training.sportsbetting.domain.FootballSportEvent;
import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.domain.SportEvent;
import com.epam.training.sportsbetting.domain.Wager;

public class DummyDataStore {
    public static final int YEAR = 2016;
    public static final int MONTH = 10;
    public static final int DAY_OF_MONTH = 16;
    public static final int HOUR = 19;
    public static final int MINUTE = 0;
    public static final BigDecimal PLAYER1_BALANCE = BigDecimal.valueOf(100);
    public static final BigDecimal PLAYER2_BALANCE = BigDecimal.valueOf(50000);
    public static final BigDecimal OUTCOME1_ODD = BigDecimal.valueOf(2);
    public static final BigDecimal OUTCOME2_ODD = BigDecimal.valueOf(4);
    public static final BigDecimal OUTCOME3_ODD = BigDecimal.valueOf(3);
    public static final BigDecimal OUTCOME4_ODD = BigDecimal.valueOf(2);
    public static final BigDecimal OUTCOME5_ODD = BigDecimal.valueOf(3);
    private final Set<Player> players;
    private final List<Bet> bets;
    private final List<Wager> wagers;
    private final SportEvent event;

    public DummyDataStore() {
        this.wagers = new ArrayList<>();
        players = generatePlayers();
        event = new FootballSportEvent("Arsenal vs Chelsea", LocalDateTime.of(YEAR, MONTH, DAY_OF_MONTH, HOUR, MINUTE));
        bets = generateOutcomes(event);
    }

    private Set<Player> generatePlayers() {
        Set<Player> players = new HashSet<>();
        Player player = Player.builder()
                .balance(PLAYER1_BALANCE)
                .name("Peter Veres")
                .email("peter_veres@epam.com")
                .currency(Currency.EUR)
                .password("peti-pwd")
                .build();
        players.add(player);
        Player player2 = Player.builder()
                .balance(PLAYER2_BALANCE)
                .name("Bertalan Fodor")
                .email("bertalan_fodor@epam.com")
                .currency(Currency.HUF)
                .password("berci-pwd")
                .build();
        players.add(player2);
        return players;
    }

    @SuppressWarnings("MethodLength")
    private List<Bet> generateOutcomes(SportEvent event) {
        Bet bet1 = createBet("player Oliver Giroud score", event);
        Bet bet2 = createBet("number of scored goals", event);
        Bet bet3 = createBet("winner", event);
        Outcome outcome1 = new Outcome.OutcomeBuilder("1")
                .setOdd(OUTCOME1_ODD)
                .setBet(bet1)
                .build();
        Outcome outcome2 = new Outcome.OutcomeBuilder("2")
                .setOdd(OUTCOME2_ODD)
                .setBet(bet1)
                .build();
        bet1.setOutcomes(List.of(outcome1, outcome2));
        Outcome outcome3 = new Outcome.OutcomeBuilder("3")
                .setOdd(OUTCOME3_ODD)
                .setBet(bet2)
                .build();
        bet2.setOutcomes(List.of(outcome3));
        Outcome outcome4 = new Outcome.OutcomeBuilder("Arsenal")
                .setOdd(OUTCOME4_ODD)
                .setBet(bet3)
                .build();
        Outcome outcome5 = new Outcome.OutcomeBuilder("Chelsea")
                .setOdd(OUTCOME5_ODD)
                .setBet(bet3)
                .build();
        bet3.setOutcomes(List.of(outcome4, outcome5));
        return List.of(bet1, bet2, bet3);
    }

    private Bet createBet(String description, SportEvent event) {
        Bet bet = new Bet();
        bet.setDescription(description);
        bet.setEvent(event);
        bet.setOutcomes(new ArrayList<>());
        return bet;
    }

    public List<Bet> getBets() {
        return new ArrayList<>(bets);
    }

    public Set<Player> getPlayers() {
        return new HashSet<>(players);
    }

    public List<Wager> getWagers() {
        return new ArrayList<>(wagers);
    }

    public void addWager(Wager wager) {
        wagers.add(wager);
    }

}

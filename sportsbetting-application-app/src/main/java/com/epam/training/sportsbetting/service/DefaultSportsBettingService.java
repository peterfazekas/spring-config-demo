package com.epam.training.sportsbetting.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.epam.training.sportsbetting.data.DummyDataStore;
import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.domain.User;
import com.epam.training.sportsbetting.domain.Wager;
import com.epam.training.sportsbetting.domain.WagerBuilder;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;
import com.epam.training.sportsbetting.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultSportsBettingService implements SportsBettingService {

    private final DummyDataStore dataStore;

    public DefaultSportsBettingService(@Autowired final DummyDataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Player authenticateUser(final User user) throws AuthenticationException {
        for (Player player : dataStore.getPlayers()) {
            if (user.equals(player)) {
                return player;
            }
        }
        throw new AuthenticationException();
    }

    @Override
    public List<Bet> findAllBets() {
        return dataStore.getBets();
    }

    @Override
    public void placeWager(final View view, final Player player, final Outcome outcome) {
        BigDecimal amount;
        boolean playerHasEnoughMoney = false;
        do {
            amount = view.requestAmountForBet();
            try {
                Wager wager = createWager(player, outcome, amount);
                playerHasEnoughMoney = true;
                view.printWagerSaved(wager);
            } catch (LowBalanceException e) {
                view.printLowBalanceMessage(player);
            }
        } while (!playerHasEnoughMoney);
    }

    private Wager createWager(final Player player, final Outcome outcome, final BigDecimal amount) throws LowBalanceException {
        if (amount.compareTo(player.getBalance()) > 0) {
            throw new LowBalanceException();
        }
        Wager wager = new WagerBuilder(amount)
                .setTimeStamp(LocalDateTime.now())
                .setCurrency(player.getCurrency())
                .setWin(outcome.getWin())
                .setOutcome(outcome)
                .setPlayer(player)
                .build();
        dataStore.addWager(wager);
        player.subtractBalance(amount);
        return wager;
    }

    @Override
    public List<Wager> findAllWagers() {
        return dataStore.getWagers();
    }

    @Override
    public void calculateResults(final WinStrategy winCondition) {
        var allWagers = findAllWagers();
        allWagers.stream()
                .filter(wager -> !wager.isProcessed())
                .forEach(wager -> {
                    wager.setProcessed(true);
                    winCondition.setWinCondition(findAllBets(), wager);
                    calculateNewBalance(wager);
                });
    }

    private void calculateNewBalance(final Wager wager) {
        if (wager.isWin()) {
            wager.getPlayer().addBalance(wager.getAmount().multiply(wager.getOutcome().getOdd()));
        }
    }


}

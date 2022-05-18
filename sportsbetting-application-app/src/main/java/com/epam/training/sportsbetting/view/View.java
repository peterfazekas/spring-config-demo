package com.epam.training.sportsbetting.view;

import java.math.BigDecimal;
import java.util.List;

import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.domain.User;
import com.epam.training.sportsbetting.domain.Wager;

public interface View {
    User readCredentials();

    void printWelcomeMessage(Player player);

    void printOutcomes(List<Bet> bets);

    Outcome selectOutcome(List<Bet> bets);

    BigDecimal requestAmountForBet();

    void printLowBalanceMessage(Player player);

    void printWagerSaved(Wager wager);

    void printResults(Player player, List<Wager> wagers);

    void printString(String string);

}

package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.domain.User;
import com.epam.training.sportsbetting.service.AuthenticationException;
import com.epam.training.sportsbetting.service.SportsBettingService;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;
import com.epam.training.sportsbetting.view.View;

public class Supporter {
    private final View view;
    private final SportsBettingService sportsBettingService;

    public Supporter(View view, SportsBettingService sportsBettingService) {
        this.view = view;
        this.sportsBettingService = sportsBettingService;
    }

    public Player getPlayerDetails() {
        User user = view.readCredentials();
        return checkIfPlayerValid(user);
    }

    @SuppressWarnings("RegexpSingleline")
    private Player checkIfPlayerValid(User user) {
        Player player = Player.builder().build();
        try {
            player = sportsBettingService.authenticateUser(user);
        } catch (AuthenticationException e) {
            view.printString("incorrect credentials");
            System.exit(0);
        }
        return player;
    }

    public void placeWagers(Player player) {
        Outcome outcome;
        do {
            outcome = getWagerFromPlayer();
            addWagerToListIfValid(player, outcome);
        } while (outcome != null);
    }

    private void addWagerToListIfValid(Player player, Outcome outcome) {
        if (outcome != null) {
            sportsBettingService.placeWager(view, player, outcome);
        }
    }

    private Outcome getWagerFromPlayer() {
        Outcome outcome;
        view.printOutcomes(sportsBettingService.findAllBets());
        outcome = view.selectOutcome(sportsBettingService.findAllBets());
        return outcome;
    }

    public void showResults(Player player, WinStrategy winStrategy) {
        sportsBettingService.calculateResults(winStrategy);
        view.printResults(player, sportsBettingService.findAllWagers());
    }

}

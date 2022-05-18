package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.service.strategy.FirstWinCondition;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;

public final class App {

    private final Supporter supporter;
    private final WinStrategy winStrategy;

    public App(Supporter supporter, WinStrategy winStrategy) {
        this.supporter = supporter;
        this.winStrategy = winStrategy;
    }

    public void play() {
        Player player = supporter.getPlayerDetails();
        supporter.placeWagers(player);
        supporter.showResults(player, new FirstWinCondition());
    }
}


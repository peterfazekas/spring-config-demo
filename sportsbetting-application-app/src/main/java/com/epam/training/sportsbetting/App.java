package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public final class App {

    private final Supporter supporter;
    private final WinStrategy winStrategy;

    public App(@Autowired Supporter supporter,
               @Autowired @Qualifier("FirstWinCondition") WinStrategy winStrategy) {
        this.supporter = supporter;
        this.winStrategy = winStrategy;
    }

    public void play() {
        Player player = supporter.getPlayerDetails();
        supporter.placeWagers(player);
        supporter.showResults(player, winStrategy);
    }
}


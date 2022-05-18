package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.data.DummyDataStore;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.service.AuthenticationException;
import com.epam.training.sportsbetting.service.DefaultSportsBettingService;
import com.epam.training.sportsbetting.service.SportsBettingService;
import com.epam.training.sportsbetting.service.strategy.FirstWinCondition;
import com.epam.training.sportsbetting.view.ConsoleView;
import com.epam.training.sportsbetting.view.DefaultConsoleIO;
import com.epam.training.sportsbetting.view.View;

public final class App {

    private final Supporter supporter;

    private App() {
        View view = new ConsoleView(new DefaultConsoleIO());
        SportsBettingService sportsBettingService = new DefaultSportsBettingService(new DummyDataStore());
        supporter = new Supporter(view, sportsBettingService);
    }

    public static void main(String[] args) throws AuthenticationException {
        new App().run();
    }

    private void run() {
        Player player = supporter.getPlayerDetails();
        supporter.placeWagers(player);
        supporter.showResults(player, new FirstWinCondition());
    }
}


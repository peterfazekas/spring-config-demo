package com.epam.training.sportsbetting.service;

import java.util.List;

import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.domain.User;
import com.epam.training.sportsbetting.domain.Wager;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;
import com.epam.training.sportsbetting.view.View;

public interface SportsBettingService {
    Player authenticateUser(User user) throws AuthenticationException;

    List<Bet> findAllBets();

    List<Wager> findAllWagers();

    void calculateResults(WinStrategy winStrategy);

    void placeWager(View view, Player player, Outcome outcome);
}

package com.epam.training.sportsbetting.service.strategy;

import java.util.List;

import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Wager;

public interface WinStrategy {
    void setWinCondition(List<Bet> bets, Wager wager);
}

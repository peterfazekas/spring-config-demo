package com.epam.training.sportsbetting.service.strategy;

import java.util.List;
import java.util.stream.Collectors;

import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Wager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("SecondWinCondition")
public class SecondWinCondition implements WinStrategy {
    @Override
    public void setWinCondition(List<Bet> bets, Wager wager) {
        Outcome playerOutcome = wager.getOutcome();
        List<Outcome> outcomes = bets.stream()
                .map(Bet::getOutcomes)
                .map(outcomes1 -> outcomes1.get(0))
                .collect(Collectors.toList());
        if (outcomes.contains(playerOutcome)) {
            wager.setWin(true);
        }
    }
}

package com.epam.training.sportsbetting.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.epam.training.sportsbetting.domain.Bet;
import com.epam.training.sportsbetting.domain.Outcome;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.domain.User;
import com.epam.training.sportsbetting.domain.Wager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleView implements View {
    public static final String QUIT = "q";
    private final ConsoleIO defaultConsoleIO;

    public ConsoleView(@Autowired final ConsoleIO consoleIO) {
        defaultConsoleIO = consoleIO;
    }


    @Override
    public User readCredentials() {
        printString("> Enter player email address:");
        String readEmail = defaultConsoleIO.readString(System.in);
        printString("> Enter player password:");
        String userPassword = defaultConsoleIO.readString(System.in);
        return User.builder()
                .email(readEmail)
                .password(userPassword)
                .build();
    }

    @Override
    public void printWelcomeMessage(final Player player) {
        printString(player.toString());
    }

    @Override
    public void printOutcomes(final List<Bet> bets) {
        AtomicInteger index = new AtomicInteger(1);
        bets.stream().flatMap(bet -> bet.getOutcomes().stream()).forEach(outcome -> printString(
                "> " + index.getAndIncrement()
                        + outcome));
    }

    @Override
    public Outcome selectOutcome(final List<Bet> bets) {
        int numberOfOutcomes = (int) bets.stream().mapToLong(bet -> bet.getOutcomes().size()).sum();
        String input;
        do {
            printString("> What do you want to bet on? (choose a number or press q for quit)");
            input = defaultConsoleIO.readString(System.in).trim();
        } while (!inputIsValid(input, numberOfOutcomes));
        return getOutcomeIfPlayerChoiceIsValid(bets, input);
    }

    private boolean inputIsValid(final String input, final int numberOfOutcomes) {
        boolean isValid;
        if (QUIT.equals(input)) {
            isValid = true;
        } else {
            try {
                int choice = Integer.parseInt(input);
                isValid = choice > 0 && choice <= numberOfOutcomes;
            } catch (NumberFormatException e) {
                isValid = false;
            }
        }
        return isValid;
    }

    private Outcome getOutcomeIfPlayerChoiceIsValid(final List<Bet> bets, final String input) {
        Outcome outcome = null;
        if (!QUIT.equals(input)) {
            int choice = Integer.parseInt(input);
            List<Outcome> outcomes = bets.stream().flatMap(bet -> bet.getOutcomes().stream()).collect(Collectors.toList());
            outcome = outcomes.get(choice - 1);
        }
        return outcome;
    }

    @Override
    public BigDecimal requestAmountForBet() {
        printString("> What amount do you wish to bet on it?");
        return BigDecimal.valueOf(Integer.parseInt(defaultConsoleIO.readString(System.in)));
    }

    @Override
    public void printLowBalanceMessage(final Player player) {
        printString("> You don't have enough money, your balance is " + player.getBalance() + " " + player.getCurrency());
    }

    @Override
    public void printWagerSaved(final Wager wager) {
        printString("\n> Wager '" + wager.getBetDescription() + "="
                + wager.getOutcomeDescription() + "' of "
                + wager.getEventTitle() + " [odd: "
                + wager.getOutcomeOdd() + ", amount: "
                + wager.getAmount() + "] saved!");
        printString("> Your balance is " + wager.getPlayerBalance() + " " + wager.getCurrency() + "\n");
    }

    @Override
    public void printResults(final Player player, final List<Wager> wagers) {
        printString("> Results:");
        wagers.forEach(wager -> printString("> Wager '" + wager.getBetDescription() + "="
                + wager.getOutcomeDescription() + "' of "
                + wager.getEventTitle() + " [odd: "
                + wager.getOutcomeOdd() + ", amount: "
                + wager.getAmount() + "], win: "
                + wager.isWin()));

        printString("> Your new balance is " + player.getBalance() + " " + player.getCurrency());
    }

    @Override
    public void printString(final String string) {
        defaultConsoleIO.println(string);
    }
}

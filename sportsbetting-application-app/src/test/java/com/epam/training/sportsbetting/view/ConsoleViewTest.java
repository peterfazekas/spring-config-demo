package com.epam.training.sportsbetting.view;

import com.epam.training.sportsbetting.FieldInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class ConsoleViewTest extends FieldInitializer {


    private ConsoleView underTest;
    @Mock
    private DefaultConsoleIO defaultConsoleIO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new ConsoleView(defaultConsoleIO);
    }

    @Test
    void testReadCredentialsShouldReturnUser()  {
        //GIVEN
        var player = createPlayer();
        given(defaultConsoleIO.readString(any())).willReturn(TEST_EMAIL_COM).willReturn(TEST_PASSWORD);
        //WHEN
        var actual = underTest.readCredentials();
        //THEN
        assertTrue(player.getEmail().equals(actual.getEmail()) && player.getPassword().equals(actual.getPassword()));
    }

    @Test
    void testPrintWelcomeMessageShouldPrintPlayerData() {
        //GIVEN
        var player = createPlayer();
        var testString = "\n> Welcome Test Player!\n> Your balance is "+PLAYER_BALANCE_START_VALUE+" "+TEST_CURRENCY+"\n";
        //WHEN
        underTest.printWelcomeMessage(player);
        //THEN
        verify(defaultConsoleIO).println(testString);
    }

    @Test
    void testPrintOutcomesShouldPrintOutcomes() {
        //GIVEN
        var bets = createBetList();
        String testString = "> 1: Sport event: "+EVENT_TITLE+" (start: 1990-05-10 12:00:00), Bet: "+BET_DESCRIPTION+", Outcome: "+OUTCOME_DESCRIPTION+", Actual odd: "+ODD+".";
        //WHEN
        underTest.printOutcomes(bets);
        //THEN
        verify(defaultConsoleIO).println(testString);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void testSelectOutcomeShouldReturnSelectedOutcomeWhenUserOutcomeValid(int choice)  {
        //GIVEN
        var bets = createBetList();
        given(defaultConsoleIO.readString(System.in)).willReturn(String.valueOf(choice));
        //WHEN
        var actual = underTest.selectOutcome(bets);
        //THEN
        assertEquals(bets.get(0).getOutcomes().get(choice-1),actual);
    }

    @Test
    void testSelectOutcomeShouldReturnNullWhenUserChooseQuit()  {
        //GIVEN
        var bets = createBetList();
        given(defaultConsoleIO.readString(any())).willReturn("q");
        //WHEN
        var actual = underTest.selectOutcome(bets);
        //THEN
        assertNull(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {10, 50})
    void requestAmountForBetShouldReturnInputNumber(long number){
        //GIVEN
        var expected  = BigDecimal.valueOf(number);
        given(defaultConsoleIO.readString(any())).willReturn(String.valueOf(number));
        //THEN
        assertEquals(expected , underTest.requestAmountForBet());
    }

    @Test
    void testPrintLowBalanceMessageShouldPrintStringWithPlayerBalanceAndCurrency() {
        //GIVEN
        var testString = "> You don't have enough money, your balance is "+PLAYER_BALANCE_START_VALUE+" "+TEST_CURRENCY;
        var player = createPlayer();
        //WHEN
        underTest.printLowBalanceMessage(player);
        //THEN
        verify(defaultConsoleIO).println(testString);
    }

    @Test
    void testPrintWagerSavedShouldPrintWagerData() {
        //GIVEN
        var player = createPlayer();
        var wager = createWinnerWager(player);
        var testString = "\n> Wager '"+BET_DESCRIPTION+"="+OUTCOME_DESCRIPTION+"' of "+EVENT_TITLE+" [odd: "+ODD+", amount: "+VALID_BET_VALUE+"] saved!";
        var testString2 = "> Your balance is "+PLAYER_BALANCE_START_VALUE+" "+TEST_CURRENCY+"\n";
        //WHEN
        underTest.printWagerSaved(wager);
        //THEN
        verify(defaultConsoleIO).println(testString);
        verify(defaultConsoleIO).println(testString2);
    }

    @Test
    void testPrintResultsShouldPrintPlayerWagerResultsAndBalance() {
        //GIVEN
        var player = createPlayer();
        var wager = createWinnerWager(player);
        var wagers = Collections.singletonList(wager);
        var testString = "> Results:";
        var testString2 = "> Wager '"+BET_DESCRIPTION+"="+OUTCOME_DESCRIPTION+"' of "+EVENT_TITLE+" [odd: "+ODD+", amount: "+VALID_BET_VALUE+"], win: true";
        var testString3 = "> Your new balance is "+PLAYER_BALANCE_START_VALUE+" "+TEST_CURRENCY;
        //WHEN
        underTest.printResults(player, wagers);
        //THEN
        verify(defaultConsoleIO).println(testString);
        verify(defaultConsoleIO).println(testString2);
        verify(defaultConsoleIO).println(testString3);
    }

}
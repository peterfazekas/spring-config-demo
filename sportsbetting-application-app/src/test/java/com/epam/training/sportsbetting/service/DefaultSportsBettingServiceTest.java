package com.epam.training.sportsbetting.service;

import com.epam.training.sportsbetting.FieldInitializer;
import com.epam.training.sportsbetting.data.DummyDataStore;
import com.epam.training.sportsbetting.domain.User;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;
import com.epam.training.sportsbetting.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

class DefaultSportsBettingServiceTest extends FieldInitializer {

    private DefaultSportsBettingService underTest;
    @Mock
    private DummyDataStore dataStore;
    @Mock
    private WinStrategy winStrategy;
    @Mock
    private View view;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new DefaultSportsBettingService(dataStore);
    }

    @Nested
    class AuthenticateUserTests {

        @Test
        void authenticateUserShouldPassWhenInputIsValid() {
            //GIVEN
            var player = createPlayer();
            var players = Collections.singleton(player);
            given(dataStore.getPlayers()).willReturn(players);
            //WHEN
            User actual = underTest.authenticateUser(VALID_USER);
            //THEN
            assertEquals(player, actual);
            verify(dataStore).getPlayers();
        }

        @Test
        void authenticateUserShouldThrowExceptionWhenEmailInputIsInvalid() {
            //GIVEN
            var player = createPlayer();
            var players = Collections.singleton(player);
            given(dataStore.getPlayers()).willReturn(players);
            //THEN
            assertThrows(AuthenticationException.class, () -> underTest.authenticateUser(INVALID_EMAIL_USER));
            verify(dataStore).getPlayers();
        }

        @Test
        void authenticateUserShouldThrowExceptionWhenPasswordInputIsInvalid() {
            //GIVEN
            var player = createPlayer();
            var players = Collections.singleton(player);
            given(dataStore.getPlayers()).willReturn(players);
            //THEN
            assertThrows(AuthenticationException.class, () -> underTest.authenticateUser(INVALID_PASSWORD_USER));
            verify(dataStore).getPlayers();
        }
    }

    @Nested
    class CalculateResultsTests {

        @Test
        void calculateResultsShouldIncreasePlayerBalanceWhenWagerIsWinner() {
            //GIVEN
            var player = createPlayer();
            var winnerWager = createWinnerWager(player);
            var expected = PLAYER_BALANCE_START_VALUE.add(ODD.multiply(VALID_BET_VALUE));
            var wagerList = Collections.singletonList(winnerWager);
            var betList = createBetList();
            given(dataStore.getWagers()).willReturn(wagerList);
            given(dataStore.getBets()).willReturn(betList);
            //WHEN
            underTest.calculateResults(winStrategy);
            //THEN
            assertEquals(expected, player.getBalance());
            verify(winStrategy).setWinCondition(betList, winnerWager);
        }

        @Test
        void calculateResultsShouldNotChangePlayerBalanceWhenWagerIsLoser() {
            //GIVEN
            var player = createPlayer();
            var loserWager = createLoserWager(player);
            var wagerList = Collections.singletonList(loserWager);
            var betList = createBetList();
            given(dataStore.getWagers()).willReturn(wagerList);
            given(dataStore.getBets()).willReturn(betList);
            //WHEN
            underTest.calculateResults(winStrategy);
            //THEN
            assertEquals(PLAYER_BALANCE_START_VALUE, player.getBalance());
        }

        @Test
        void calculateResultsShouldNotChangePlayerBalanceWhenWagerIsAlreadyProcessed() {
            //GIVEN
            var player = createPlayer();
            var alreadyProcessedWager = createProcessedWager(player);
            var wagerList = Collections.singletonList(alreadyProcessedWager);
            var betList = createBetList();
            given(dataStore.getWagers()).willReturn(wagerList);
            given(dataStore.getBets()).willReturn(betList);
            //WHEN
            underTest.calculateResults(winStrategy);
            //THEN
            assertEquals(PLAYER_BALANCE_START_VALUE, player.getBalance());
            verify(winStrategy, Mockito.never()).setWinCondition(betList, alreadyProcessedWager);
        }
    }

    @Nested
    class PlaceWagerTests {
        @Test
        void testPlaceWagerShouldPrintLowBalanceMessageWhenCatchLowBalanceException() {
            //GIVEN
            var player = createPlayer();
            var outcome = createOutcome(createBet());
            given(view.requestAmountForBet()).willReturn(INVALID_BET_VALUE).willReturn(VALID_BET_VALUE);
            //WHEN
            underTest.placeWager(view, player, outcome);
            //THEN
            verify(view, BDDMockito.times(1)).printLowBalanceMessage(player);
            verify(view, BDDMockito.times(1)).printWagerSaved(BDDMockito.any());
        }

        @Test
        void testPlaceWagerShouldPrintWagerSavedWhenPlayerHasEnoughMoney() {
            //GIVEN
            var player = createPlayer();
            var outcome = createOutcome(createBet());
            given(view.requestAmountForBet()).willReturn(VALID_BET_VALUE);
            //WHEN
            underTest.placeWager(view, player, outcome);
            //THEN
            verify(view).printWagerSaved(BDDMockito.any());
            verify(view, Mockito.never()).printLowBalanceMessage(player);
        }

    }

    @Test
    void testFindAllBetsShouldInvokeGetBetsInDataStore() {
        //WHEN
        underTest.findAllBets();
        //THEN
        verify(dataStore).getBets();
    }

    @Test
    void testFindAllWagersShouldInvokeGetWagersInDataStore() {
        //WHEN
        underTest.findAllWagers();
        //THEN
        verify(dataStore).getWagers();
    }




}
package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.data.DummyDataStore;
import com.epam.training.sportsbetting.domain.Player;
import com.epam.training.sportsbetting.service.SportsBettingService;
import com.epam.training.sportsbetting.service.strategy.WinStrategy;
import com.epam.training.sportsbetting.view.DefaultConsoleIO;
import com.epam.training.sportsbetting.view.View;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class SupporterTest extends FieldInitializer {
    private static final Player player = createPlayer();
    @Mock
    private DefaultConsoleIO defaultConsoleIO;
    @Mock
    private DummyDataStore dummyDataStore;
    @Mock
    private View view;
    @Mock
    private SportsBettingService sportsBettingService;
    @Mock
    private WinStrategy winCondition;

    private Supporter underTest;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new Supporter(view, sportsBettingService);
    }

    @Test
    void testGetPlayerDetailsShouldReturnPlayerWhenAuthenticationIsCorrect() {
        //GIVEN
        given(view.readCredentials()).willReturn(player);
        given(sportsBettingService.authenticateUser(player)).willReturn(player);
        //WHEN
        var actual = underTest.getPlayerDetails();
        //THEN
        assertEquals(player, actual);
        verify(defaultConsoleIO, Mockito.never()).println(BDDMockito.any());
    }

    @Test
    void placeWagersShouldPrintOutcomesAndPlaceWagerWhenOutcomeIsNotNull() {
        var expected = createOutcome(createBet());
        given(view.selectOutcome(dummyDataStore.getBets()))
                .willReturn(expected).willReturn(null);
        //WHEN
        underTest.placeWagers(player);
        //THEN
        verify(sportsBettingService, times(1)).placeWager(view, player, expected);
    }

    @Test
    void showResultsShouldCalculateResultsAndPrintThem() {
        //WHEN
        underTest.showResults(player, winCondition);
        //THEN
        verify(sportsBettingService).calculateResults(winCondition);
        verify(view).printResults(player, sportsBettingService.findAllWagers());
    }
}
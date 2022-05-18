package com.epam.training.sportsbetting.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultConsoleIOTest {

    private DefaultConsoleIO underTest;

    @BeforeEach
    public void setUp() {
        underTest = new DefaultConsoleIO();
    }

    @ParameterizedTest
    @ValueSource(strings = {"testThis", "TESTthat"})
    void testPrintlnShouldPrintInputString(String input) {
        //GIVEN
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        //WHEN
        underTest.println(input);
        //THEN
        assertEquals(input, outputStreamCaptor
                .toString()
                .trim());
        System.setOut(System.out);
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "TEST"})
    void testReadLineShouldReadInputString(String input) {
        //GIVEN
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        //WHEN
        String actual = underTest.readString(in);
        //THEN
        assertEquals(input, actual);
    }
}
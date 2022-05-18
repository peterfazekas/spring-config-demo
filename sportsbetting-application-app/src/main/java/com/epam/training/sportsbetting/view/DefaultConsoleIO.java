package com.epam.training.sportsbetting.view;

import java.io.InputStream;
import java.util.Scanner;

public class DefaultConsoleIO implements ConsoleIO {

    @Override
    public void println(final String string) {
        System.out.println(string);
    }

    @Override
    public String readString(final InputStream in) {
        return new Scanner(in).nextLine();
    }
}

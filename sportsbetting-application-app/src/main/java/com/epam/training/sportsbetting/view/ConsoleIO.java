package com.epam.training.sportsbetting.view;

import java.io.InputStream;


public interface ConsoleIO {
    void println(String string);

    String readString(InputStream in);
}

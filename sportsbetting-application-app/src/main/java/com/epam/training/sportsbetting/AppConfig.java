package com.epam.training.sportsbetting;

import com.epam.training.sportsbetting.data.DummyDataStore;
import com.epam.training.sportsbetting.service.DefaultSportsBettingService;
import com.epam.training.sportsbetting.service.SportsBettingService;
import com.epam.training.sportsbetting.service.strategy.FirstWinCondition;
import com.epam.training.sportsbetting.view.ConsoleView;
import com.epam.training.sportsbetting.view.DefaultConsoleIO;
import com.epam.training.sportsbetting.view.View;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public View consoleView() {
        return new ConsoleView(new DefaultConsoleIO());
    }

    @Bean
    public SportsBettingService sportsBettingService() {
        return new DefaultSportsBettingService(new DummyDataStore());
    }

    @Bean
    public Supporter supporter() {
        return new Supporter(consoleView(), sportsBettingService());
    }

    @Bean
    public App app() {
        return new App(supporter(), new FirstWinCondition());
    }

}

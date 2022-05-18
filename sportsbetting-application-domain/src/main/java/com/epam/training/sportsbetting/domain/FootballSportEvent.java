package com.epam.training.sportsbetting.domain;

import java.time.LocalDateTime;

public class FootballSportEvent extends SportEvent {

    public FootballSportEvent(String title, LocalDateTime startDate) {
        super(title, startDate);

    }
}

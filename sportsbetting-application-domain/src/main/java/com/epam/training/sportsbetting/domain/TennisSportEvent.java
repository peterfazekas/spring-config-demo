package com.epam.training.sportsbetting.domain;


import java.time.LocalDateTime;

public class TennisSportEvent extends SportEvent {

    public TennisSportEvent(String title, LocalDateTime startDate) {
        super(title,startDate);
    }
}

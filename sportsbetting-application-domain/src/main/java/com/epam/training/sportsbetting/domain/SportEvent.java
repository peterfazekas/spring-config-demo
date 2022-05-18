package com.epam.training.sportsbetting.domain;

import java.time.LocalDateTime;


public abstract class SportEvent {
    private final String title;
    private final LocalDateTime startDate;

    public SportEvent(String title, LocalDateTime startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public String getTitle() {
        return title;
    }
}

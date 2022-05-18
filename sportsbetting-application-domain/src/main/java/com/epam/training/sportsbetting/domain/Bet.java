package com.epam.training.sportsbetting.domain;

import java.util.ArrayList;
import java.util.List;

public class Bet {
    private String description;
    private List<Outcome> outcomes;
    private SportEvent event;


    public synchronized void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Outcome> getOutcomes() {
        return new ArrayList<>(outcomes);
    }

    public synchronized void setOutcomes(List<Outcome> outcomes) {
        this.outcomes = new ArrayList<>(outcomes);
    }

    public SportEvent getEvent() {
        return event;
    }

    public synchronized void setEvent(SportEvent event) {
        this.event = event;
    }

}

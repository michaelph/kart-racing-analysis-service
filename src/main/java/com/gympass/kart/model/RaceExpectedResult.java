package com.gympass.kart.model;

import java.time.Duration;

/**
 * @author michael
 */
public class RaceExpectedResult {

    private Integer pilotPosition;
    private String pilotCode;
    private String pilotName;
    private Integer laps;
    private Duration racingDuration;


    public RaceExpectedResult() {
    }

    public RaceExpectedResult(Integer pilotPosition, String pilotCode, String pilotName,
        Integer laps,
        Duration racingDuration) {
        this.pilotPosition = pilotPosition;
        this.pilotCode = pilotCode;
        this.pilotName = pilotName;
        this.laps = laps;
        this.racingDuration = racingDuration;


    }

    public String getPilotCode() {
        return pilotCode;
    }

    public void setPilotCode(String pilotCode) {
        this.pilotCode = pilotCode;
    }

    public String getPilotName() {
        return pilotName;
    }

    public void setPilotName(String pilotName) {
        this.pilotName = pilotName;
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }


    public Integer getPilotPosition() {
        return pilotPosition;
    }

    public void setPilotPosition(Integer pilotPosition) {
        this.pilotPosition = pilotPosition;
    }

    public Duration getRacingDuration() {
        return racingDuration;
    }

    public void setRacingDuration(Duration racingDuration) {
        this.racingDuration = racingDuration;
    }

    public Duration getTimeAfter(Duration duration) {
        return this.getRacingDuration().minus(duration);
    }
}

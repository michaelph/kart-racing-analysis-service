package com.gympass.kart.model;

import java.time.Duration;

/**
 * @author michael
 */
public class RaceOtherResult {

    private Integer pilotPosition;
    private String pilotCode;
    private String pilotName;
    private Integer bestLapNumber;
    private Duration bestLapTime;
    private Boolean bestLapOverall;
    private Float meanSpeed;

    public RaceOtherResult() {
    }

    public Integer getPilotPosition() {
        return this.pilotPosition;
    }

    public void setPilotPosition(Integer pilotPosition) {
        this.pilotPosition = pilotPosition;
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

    public Integer getBestLapNumber() {
        return bestLapNumber;
    }


    public void setBestLapNumber(Integer bestLapNumber) {
        this.bestLapNumber = bestLapNumber;
    }

    public Boolean getBestLapOverall() {
        return bestLapOverall;
    }

    public void setBestLapOverall(Boolean bestLapOverall) {
        this.bestLapOverall = bestLapOverall;
    }

    public Float getMeanSpeed() {
        return meanSpeed;
    }

    public void setMeanSpeed(Float meanSpeed) {
        this.meanSpeed = meanSpeed;
    }

    public Duration getBestLapTime() {
        return bestLapTime;
    }

    public void setBestLapTime(Duration bestLapTime) {
        this.bestLapTime = bestLapTime;
    }
}

package com.gympass.kart.model;

import java.time.Duration;
import java.time.LocalTime;

/**
 * @author michael
 */
public class KartLog implements Log {

    private LocalTime checkTime;
    private String pilotCode;
    private String pilotName;
    private Integer lapNumber;
    private Duration lapDuration;
    private Float meanLapSpeed;

    public KartLog() {
    }


    public LocalTime getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(LocalTime checkTime) {
        this.checkTime = checkTime;
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

    public Integer getLapNumber() {
        return lapNumber;
    }

    public void setLapNumber(Integer lapNumber) {
        this.lapNumber = lapNumber;
    }

    public Duration getLapDuration() {
        return lapDuration;
    }

    public void setLapDuration(Duration lapDuration) {
        this.lapDuration = lapDuration;
    }

    public Float getMeanLapSpeed() {
        return meanLapSpeed;
    }

    public void setMeanLapSpeed(Float meanLapSpeed) {
        this.meanLapSpeed = meanLapSpeed;
    }

    @Override
    public String timestamp() {
        return this.checkTime.toString();
    }
}

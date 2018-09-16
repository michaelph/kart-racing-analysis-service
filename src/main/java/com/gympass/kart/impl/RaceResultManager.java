package com.gympass.kart.impl;

import com.gympass.kart.model.KartLog;
import com.gympass.kart.model.RaceExpectedResult;
import com.gympass.kart.model.RaceOtherResult;
import com.gympass.kart.service.Results;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author michael
 */
public class RaceResultManager implements Results {


    private List<RaceExpectedResult> raceExpectedResults;
    private List<RaceOtherResult> raceOtherResults;


    public Boolean getResults(List<KartLog> kartLogs) {
        AtomicInteger counter = new AtomicInteger(1);

        //order the kartlog_valid logs by number of laps completed and then order the kartlog_valid logs by checkTime
        kartLogs.sort(Comparator.comparingInt(KartLog::getLapNumber).reversed()
            .thenComparing(KartLog::getCheckTime));

        //consolidating kartlog_valid logs
        LinkedHashMap<String, List<KartLog>> consolidatedKartLogs = reduceKartLogByPilotCode(
            kartLogs);

        setRaceExpectedResults(new ArrayList<>(consolidatedKartLogs.size()));

        consolidatedKartLogs.values().forEach(kartLogsValue -> {
            //get the first element because list is already ordered
            KartLog kartLog = kartLogsValue.get(0);
            RaceExpectedResult raceExpectedResult = new RaceExpectedResult();
            raceExpectedResult.setPilotPosition(counter.getAndIncrement());
            raceExpectedResult.setPilotCode(kartLog.getPilotCode());
            raceExpectedResult.setPilotName(kartLog.getPilotName());
            //higher lap number
            raceExpectedResult.setLaps(kartLog.getLapNumber());
            Duration raceDuration = getPilotRaceDuration(kartLogsValue);
            raceExpectedResult.setRacingDuration(raceDuration);
            getRaceExpectedResults().add(raceExpectedResult);

        });

        getOtherResults(kartLogs, consolidatedKartLogs);
        return true;
    }

    private void getOtherResults(List<KartLog> orderedKartLogs,
        LinkedHashMap<String, List<KartLog>> consolidatedKartLogs) {
        AtomicInteger counter = new AtomicInteger(1);
        setRaceOtherResults(new ArrayList<>(consolidatedKartLogs.size()));

        orderedKartLogs.sort(Comparator.comparing(KartLog::getLapDuration));
        KartLog kartLogWithBestLapTime = orderedKartLogs.get(0);

        consolidatedKartLogs.values().forEach(kartLogsValue -> {
            KartLog kartLog = kartLogsValue.get(0);
            RaceOtherResult raceOtherResult = new RaceOtherResult();
            raceOtherResult.setPilotPosition(counter.getAndIncrement());
            raceOtherResult.setPilotCode(kartLog.getPilotCode());
            raceOtherResult.setPilotName(kartLog.getPilotName());
            raceOtherResult.setBestLapNumber(findBestLap(kartLogsValue).getLapNumber());
            raceOtherResult.setMeanSpeed(calculateMeanSpeed(kartLogsValue));
            Boolean isBest = raceOtherResult.getPilotCode()
                .equals(kartLogWithBestLapTime.getPilotCode());
            raceOtherResult.setBestLapOverall(isBest);
            raceOtherResult.setBestLapTime(
                isBest ? kartLogWithBestLapTime.getLapDuration() : kartLog.getLapDuration());
            getRaceOtherResults().add(raceOtherResult);
        });
    }

    public Duration getPilotRaceDuration(List<KartLog> kartLogs) {
        KartLog firstLap = kartLogs.get(kartLogs.size() - 1);
        KartLog lastLap = kartLogs.get(0);
        return Duration.between(firstLap.getCheckTime(), lastLap.getCheckTime())
            .plus(firstLap.getLapDuration());
    }

    private static LinkedHashMap<String, List<KartLog>> reduceKartLogByPilotCode(
        List<KartLog> kartLogs) {

        //reducing kartlog_valid logs by pilot code respecting the order
        LinkedHashMap<String, List<KartLog>> consolidatedKartLogs = new LinkedHashMap<>();

        //TODO map reduce here!
        kartLogs.forEach(kartLog -> {
            String pilotCode = kartLog.getPilotCode();
            if (!consolidatedKartLogs.containsKey(pilotCode)) {
                consolidatedKartLogs.put(pilotCode, new ArrayList<>(Arrays.asList(kartLog)));
            } else {
                consolidatedKartLogs.get(pilotCode).add(kartLog);
            }

        });

        return consolidatedKartLogs;
    }

    public KartLog findBestLap(List<KartLog> kartLogs) {

        kartLogs.sort(Comparator.comparing(KartLog::getLapDuration));
        //Assume karLogs to be ordered by lap time
        KartLog kartLogWithBestLapDuration = kartLogs.get(0);

        return kartLogWithBestLapDuration;
    }

    public Float calculateMeanSpeed(List<KartLog> kartLogs) {
        //first element has the high lap number
        kartLogs.sort(Comparator.comparingInt(KartLog::getLapNumber).reversed());

        Float meanSpeed = 0.0F;
        for (KartLog kartLog : kartLogs) {
            meanSpeed += kartLog.getMeanLapSpeed();
        }
        meanSpeed = meanSpeed / kartLogs.get(0).getLapNumber();

        return meanSpeed;


    }

    public List<RaceExpectedResult> getRaceExpectedResults() {
        return raceExpectedResults;
    }

    public void setRaceExpectedResults(
        List<RaceExpectedResult> raceExpectedResults) {
        this.raceExpectedResults = raceExpectedResults;
    }

    public List<RaceOtherResult> getRaceOtherResults() {
        return raceOtherResults;
    }

    public void setRaceOtherResults(List<RaceOtherResult> raceOtherResults) {
        this.raceOtherResults = raceOtherResults;
    }
}

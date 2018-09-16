package com.gympass.kart.impl;

import com.gympass.kart.model.KartLog;
import com.gympass.kart.service.Decoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author michael
 */
public class KartLogDecoder implements Decoder {

    private static Logger logger = Logger.getLogger(KartLogDecoder.class.getName());

    public KartLogDecoder() {
    }

    public List<KartLog> decode(File file) throws Exception {

        ArrayList<KartLog> kartLogs = new ArrayList();
        Scanner a = new Scanner(new FileReader(file));
        a.next();
        try (Scanner s = new Scanner(new FileReader(file))) {
            s.nextLine();
            while (s.hasNext()) {
                String fileLine = s.nextLine();
                //split by one or more empty strings
                String[] columns = fileLine.split("\\s+");
                if (columns.length != 7) {
                    throw new Exception("File input with Wrong number of columns!");
                }

                KartLog kartLog = new KartLog();
                LocalTime ckeckTime = getLocalTime(columns[0], "HH:mm:ss.SSS");
                kartLog.setCheckTime(ckeckTime);
                kartLog.setPilotCode(getPilotCode(columns[1]));
                kartLog.setPilotName(getPilotName(columns[3]));
                kartLog.setLapNumber(Integer.valueOf(columns[4]));
                Duration duration = getDuration(columns[5]);
                kartLog.setLapDuration(duration);
                kartLog.setMeanLapSpeed(Float.valueOf(columns[6].replace(",", ".")));
                //add kartlog_valid log to the unordered list
                kartLogs.add(kartLog);

            }
        }
        return kartLogs;

    }

    public String getPilotName(String columnValue) throws Exception {
        if (!columnValue.matches("^[a-zA-Z.]+$")) {
            throw new Exception("Not allowed characters in pilot name!");
        }
        return columnValue;
    }


    public String getPilotCode(String columnValue) throws Exception {
        if (!columnValue.matches("^[a-zA-Z0-9]+$")) {
            throw new Exception("Not allowed characters in pilot code!");
        }
        return columnValue;

    }

    public LocalTime getLocalTime(String localTimeString, String format) {
        LocalTime localTime = LocalTime
            .parse(localTimeString, DateTimeFormatter.ofPattern(format));
        return localTime;
    }

    public Duration getDuration(String durationString) {
        String[] durationSplit = durationString.split(":");
        Duration duration = Duration
            .parse("PT" + durationSplit[0] + "M" + durationSplit[1] + "S");
        return duration;
    }


}

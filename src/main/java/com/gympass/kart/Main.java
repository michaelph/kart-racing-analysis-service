package com.gympass.kart;

import com.gympass.kart.impl.KartLogDecoder;
import com.gympass.kart.impl.RaceResultManager;
import com.gympass.kart.impl.RaceResultOutputFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author michael
 */
public class Main {

    private static Logger logger = Logger.getLogger(Main.class.getName());
    private static int TIMEOUT = 1;

    public static void main(String[] args) {
        RaceResultManager raceResultManager = new RaceResultManager();
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        try {

            validateFileInput(args);

            CompletableFuture.supplyAsync(() -> {
                System.out.println("Decoding...");
                //decode input
                try {
                    return kartLogDecoder.decode(new File(args[0]));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage());
                }
                return null;
            }).thenApply(kartLogs -> {
                if (kartLogs != null && !kartLogs.isEmpty()) {
                    System.out.println("Logs decoded!");
                    //processing logs
                    return raceResultManager.getResults(kartLogs);
                }
                return false;
            }).thenApply(results -> {
                if (results) {
                    System.out.println("Results ready!");
                    //show expected results
                    return RaceResultOutputFormatter
                        .showExpectedResults(raceResultManager.getRaceExpectedResults());
                }
                return false;

            }).thenApply(expectedResultsShowed -> {
                if (expectedResultsShowed) {
                    //show optional results
                    return RaceResultOutputFormatter
                        .showOtherResults(raceResultManager.getRaceOtherResults());
                }
                return false;

            }).thenAccept(success -> {
                if (success) {
                    System.out.println("Results printed!");
                }
            }).get(validProcessingTimeInput(args) ? Integer.valueOf(args[1]) : TIMEOUT,
                TimeUnit.MINUTES);


        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());

        } catch (TimeoutException e) {
            logger.log(Level.SEVERE, "Log processing time exceeded!");

        } catch (Exception e) {
            logger.log(Level.SEVERE, " An unexpected error was detected!");
        }

    }

    public static void validateFileInput(String[] args) throws FileNotFoundException {

        Optional.of(args.length)
            .filter(argsLenght -> argsLenght > 0)
            .filter(argsLenght -> new File(args[0]).exists())
            .orElseThrow(
                () -> new FileNotFoundException("Program params error. Empty or invalid"));


    }

    private static Boolean validProcessingTimeInput(String[] args) {
        try {
            if (args.length > 1 && Integer.valueOf(args[1]) > 1) {
                return true;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Invalid processing time value!");
        }

        return false;

    }
}
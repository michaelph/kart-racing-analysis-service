package com.gympass.kart.impl;

import com.gympass.kart.model.RaceExpectedResult;
import com.gympass.kart.model.RaceOtherResult;
import com.gympass.kart.service.OutputFomatter;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author michael
 */
public class RaceResultOutputFormatter implements OutputFomatter {

    // Posição Chegada, Código Piloto, Nome Piloto, Qtde Voltas Completadas e Tempo Total de Prova
    private static String alignFormatExpectedResults = "| %-24s | %-24s | %-24s | %-24s  | %-34s  |%n";
    // Posição Chegada, Código PilotoN, Nome Piloto, Melhor Volta, Velocidade Média
    private static String alignFormatOtherStatistics = "| %-24s | %-24s | %-24s |%n";

    private static void printHeader() {
        String header = "\n"
            + "                           _  __          _     _____                                       _           _     \n"
            + "                          | |/ /         | |   |  __ \\                    /\\               | |         (_)    \n"
            + "                          | ' / __ _ _ __| |_  | |__) |__ _  ___ ___     /  \\   _ __   __ _| |_   _ ___ _ ___ \n"
            + "                          |  < / _` | '__| __| |  _  // _` |/ __/ _ \\   / /\\ \\ | '_ \\ / _` | | | | / __| / __|\n"
            + "                          | . \\ (_| | |  | |_  | | \\ \\ (_| | (_|  __/  / ____ \\| | | | (_| | | |_| \\__ \\ \\__ \\\n"
            + "                          |_|\\_\\__,_|_|   \\__| |_|  \\_\\__,_|\\___\\___| /_/    \\_\\_| |_|\\__,_|_|\\__, |___/_|___/\n"
            + "                                                                                               __/ |          \n"
            + "                                                                                              |___/           \n";
        System.out.println(header);
    }

    public static Boolean showExpectedResults(List<RaceExpectedResult> raceExpectedResults) {
        printHeader();

        System.out.format(
            "+--------------------------+--------------------------+--------------------------+---------------------------+-------------------------------------+%n");
        System.out.format(
            "|      Posição Chegada     |       Código Piloto      |         Nome Piloto      |   Qtde Voltas Completadas |         Tempo Total de Prova        |%n");
        System.out.format(
            "+--------------------------+--------------------------+--------------------------+---------------------------+-------------------------------------+%n");

        Duration bestTime = raceExpectedResults.get(0).getRacingDuration();
        raceExpectedResults.forEach(raceExpectedResult -> {
            Duration duration = raceExpectedResult.getRacingDuration();
            Duration afterBestTime = raceExpectedResult.getTimeAfter(bestTime);
            String prettyRacingDuration = prettyDurationFormat(duration);
            String prettyAfter = "     +" + prettyDurationFormat(afterBestTime);
            if (raceExpectedResult.getPilotPosition().equals(1)) {
                prettyAfter = "";
            }

            System.out.format(alignFormatExpectedResults,
                raceExpectedResult.getPilotPosition().toString(),
                raceExpectedResult.getPilotCode(), raceExpectedResult.getPilotName(),
                raceExpectedResult.getLaps().toString(),
                prettyRacingDuration + prettyAfter);
            System.out.format(
                "+--------------------------+--------------------------+--------------------------+----------------------------+------------------------------------+%n");
        });

        return true;

    }

    public static Boolean showOtherResults(List<RaceOtherResult> raceOtherResults) {
        System.out.println();
        System.out.println(
            "                               OUTRAS ESTATÍSTICAS                               ");
        System.out.format(
            "+--------------------------+--------------------------+--------------------------+%n");
        System.out.format(
            "|            Piloto        |        Melhor Volta      |      Velocidade Média    |%n");
        System.out.format(
            "+--------------------------+--------------------------+--------------------------+%n");

        raceOtherResults.forEach(raceOtherResult -> {

            System.out
                .format(alignFormatOtherStatistics,
                    raceOtherResult.getPilotCode() + " – " + raceOtherResult.getPilotName(),
                    raceOtherResult.getBestLapNumber().toString() + " - " + prettyDurationFormat(
                        raceOtherResult.getBestLapTime()) + (
                        raceOtherResult.getBestLapOverall() == true ? " (Best)" : ""),
                    raceOtherResult.getMeanSpeed().toString().replace(".", ","));

        });

        System.out.format(
            "+--------------------------+--------------------------+--------------------------+%n");
        return true;
    }


    private static String prettyDurationFormat(Duration duration) {
        String prettyRacingDuration;
        if (duration.toDays() == 0 && (duration.toHours() - TimeUnit.DAYS.toHours(duration.toDays())
            == 0)) {
            prettyRacingDuration = String
                .format("%sm %ss %sms",
                    duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours()),
                    duration.getSeconds() - TimeUnit.MINUTES.toSeconds(duration.toMinutes()),
                    duration.getNano() / 1000000);
        } else {

            prettyRacingDuration = String
                .format("%s dias %sh %sm %ss", duration.toDays(),
                    duration.toHours() - TimeUnit.DAYS.toHours(duration.toDays()),
                    duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours()),
                    duration.getSeconds() - TimeUnit.MINUTES.toSeconds(duration.toMinutes()));
        }
        return prettyRacingDuration;
    }


}
